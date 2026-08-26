package movil.ratemypc.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.NotificationItem
import movil.ratemypc.data.NotificationType
import movil.ratemypc.data.local.LocalNotificationsProvider
import movil.ratemypc.ui.screens.notifications.NotificationsComponents.EmptyNotifications
import movil.ratemypc.ui.screens.notifications.NotificationsComponents.NotificationFilterTabs
import movil.ratemypc.ui.screens.notifications.NotificationsComponents.NotificationItemCard
import movil.ratemypc.ui.screens.notifications.NotificationsComponents.NotificationsHeader
import movil.ratemypc.ui.theme.RateMyPcTheme
import movil.ratemypc.viewmodel.NotificationViewModel

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = viewModel(),
    navController: NavController
) {
    val notifications by viewModel.notifications.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val filteredNotifications = notifications.filter { notification ->
        when (selectedFilter) {
            NotificationFilter.ALL -> true
            NotificationFilter.FOLLOWS -> notification.type == NotificationType.FOLLOW
            NotificationFilter.COMMENTS -> notification.type == NotificationType.COMMENT
            NotificationFilter.LIKES -> notification.type == NotificationType.LIKE
        }
    }

    NotificationsScreenContent(
        modifier = modifier,
        notifications = filteredNotifications,
        unreadCount = notifications.count { !it.isRead },
        selectedFilter = selectedFilter,
        onFilterChange = viewModel::selectFilter,
        onMarkAllRead = viewModel::markAllAsRead,
        onNotificationClick = viewModel::markAsRead
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
        NotificationsScreen(navController = rememberNavController())
    }
}
