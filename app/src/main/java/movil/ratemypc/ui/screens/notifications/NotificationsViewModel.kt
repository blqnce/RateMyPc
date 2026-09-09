package movil.ratemypc.ui.screens.notifications

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.NotificationFilter
import movil.ratemypc.data.local.LocalNotificationsProvider

import javax.inject.Inject

// ViewModel para la pantalla de Notificaciones.
// Gestiona el listado de notificaciones del usuario, permitiendo filtrar y marcarlas como leídas.
@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState(
        notifications = LocalNotificationsProvider.notifications
    ))
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    // Aplica un filtro a la lista de notificaciones (Leídas, No leídas, Todas).
    fun onFilterChange(filter: NotificationFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    // Marca todas las notificaciones actuales como leídas.
    fun markAllAsRead() {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.map { it.copy(isRead = true) })
        }
    }

    // Marca una notificación específica como leída al hacer clic en ella.
    fun onNotificationClick(notificationId: String) {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.map { notification ->
                if (notification.id == notificationId) notification.copy(isRead = true) else notification
            })
        }
    }
}
