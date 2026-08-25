package movil.ratemypc.ui.screens.notifications.NotificationsComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import movil.ratemypc.R
import movil.ratemypc.data.NotificationFilter

@Composable
fun NotificationFilterTabs(
    modifier: Modifier = Modifier,
    selectedFilter: NotificationFilter,
    onFilterChange: (NotificationFilter) -> Unit
) {
    val filters = listOf(
        NotificationFilter.ALL to R.string.notifications_filter_all,
        NotificationFilter.FOLLOWS to R.string.notifications_filter_follows,
        NotificationFilter.COMMENTS to R.string.notifications_filter_comments,
        NotificationFilter.LIKES to R.string.notifications_filter_likes
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { (filter, label) ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterChange(filter) },
                label = { Text(stringResource(label)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}