package movil.ratemypc.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.NotificationItem
import movil.ratemypc.data.NotificationType
import movil.ratemypc.ui.screens.notifications.NotificationsComponents.*
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val filteredNotifications = uiState.notifications.filter { notification ->
        when (uiState.selectedFilter) {
            NotificationFilter.ALL -> true
            NotificationFilter.FOLLOWS -> notification.type == NotificationType.FOLLOW
            NotificationFilter.COMMENTS -> notification.type == NotificationType.COMMENT
            NotificationFilter.LIKES -> notification.type == NotificationType.LIKE
        }
    }

    NotificationsScreenContent(
        modifier = modifier,
        notifications = filteredNotifications,
        unreadCount = uiState.unreadCount,
        selectedFilter = uiState.selectedFilter,
        onFilterChange = { viewModel.onFilterChange(it) },
        onMarkAllRead = { viewModel.markAllAsRead() },
        onNotificationClick = { viewModel.onNotificationClick(it) }
    )
}

@Composable
fun NotificationsScreenContent(
    modifier: Modifier = Modifier,
    notifications: List<NotificationItem>,
    unreadCount: Int,
    selectedFilter: NotificationFilter,
    onFilterChange: (NotificationFilter) -> Unit,
    onMarkAllRead: () -> Unit,
    onNotificationClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NotificationsHeader(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            unreadCount = unreadCount,
            onMarkAllRead = onMarkAllRead
        )
        NotificationFilterTabs(
            modifier = Modifier.padding(horizontal = 20.dp),
            selectedFilter = selectedFilter,
            onFilterChange = onFilterChange
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (notifications.isEmpty()) {
                item { EmptyNotifications(modifier = Modifier.fillParentMaxSize()) }
            } else {
                items(notifications, key = { it.id }) { notification ->
                    NotificationItemCard(
                        notification = notification,
                        onClick = { onNotificationClick(notification.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    RateMyPcTheme {
        NotificationsScreen(navController = rememberNavController(), viewModel = NotificationsViewModel())
    }
}
