package movil.ratemypc.ui.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import movil.ratemypc.data.repository.AuthRepository
import javax.inject.Inject

// ViewModel para la pantalla de Configuración.
// Gestiona las preferencias del usuario, como seguridad, notificaciones y privacidad.
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState(
        email = authRepository.currentUser?.email ?: ""
    ))
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    // Habilita o deshabilita la autenticación de dos factores.
    fun onTwoFactorChange(enabled: Boolean) {
        _uiState.update { it.copy(twoFactorEnabled = enabled) }
    }

    // Habilita o deshabilita las notificaciones push.
    fun onPushNotificationsChange(enabled: Boolean) {
        _uiState.update { it.copy(pushNotificationsEnabled = enabled) }
    }

    // Habilita o deshabilita las notificaciones por correo electrónico.
    fun onEmailNotificationsChange(enabled: Boolean) {
        _uiState.update { it.copy(emailNotificationsEnabled = enabled) }
    }

    // Define si el perfil del usuario es visible para otros.
    fun onProfileVisibleChange(enabled: Boolean) {
        _uiState.update { it.copy(profileVisible = enabled) }
    }

    // Permite o restringe que otros usuarios sigan la cuenta.
    fun onFollowersAllowedChange(enabled: Boolean) {
        _uiState.update { it.copy(followersAllowed = enabled) }
    }

    // Alterna el estado de cambio de avatar.
    fun onAvatarChange() {
        _uiState.update { it.copy(avatarChanged = !it.avatarChanged) }
    }

    // Muestra u oculta el diálogo de confirmación para eliminar la cuenta.
    fun setShowDeleteDialog(show: Boolean) {
        _uiState.update { it.copy(showDeleteDialog = show) }
    }
}
