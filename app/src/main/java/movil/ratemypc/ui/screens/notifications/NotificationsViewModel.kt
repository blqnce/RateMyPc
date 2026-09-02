package movil.ratemypc.ui.screens.notifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.local.LocalNotificationsProvider

class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState(
        notifications = LocalNotificationsProvider.notifications
    ))
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    fun onFilterChange(filter: NotificationFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun markAllAsRead() {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.map { it.copy(isRead = true) })
        }
    }

    fun onNotificationClick(notificationId: String) {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.map { notification ->
                if (notification.id == notificationId) notification.copy(isRead = true) else notification
            })
        }
    }
}
