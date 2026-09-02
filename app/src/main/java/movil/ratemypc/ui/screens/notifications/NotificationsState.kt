package movil.ratemypc.ui.screens.notifications

import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.NotificationItem

data class NotificationsState(
    val notifications: List<NotificationItem> = emptyList(),
    val selectedFilter: NotificationFilter = NotificationFilter.ALL
) {
    val unreadCount: Int get() = notifications.count { !it.isRead }
}
