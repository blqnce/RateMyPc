package movil.ratemypc.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.NotificationItem
import movil.ratemypc.data.local.LocalNotificationsProvider

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableStateFlow(LocalNotificationsProvider.notifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _selectedFilter = MutableStateFlow(NotificationFilter.ALL)
    val selectedFilter: StateFlow<NotificationFilter> = _selectedFilter.asStateFlow()

    fun selectFilter(filter: NotificationFilter) {
        _selectedFilter.value = filter
    }

    fun markAllAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    fun markAsRead(notificationId: String) {
        _notifications.value = _notifications.value.map { notification ->
            if (notification.id == notificationId) notification.copy(isRead = true) else notification
        }
    }
}