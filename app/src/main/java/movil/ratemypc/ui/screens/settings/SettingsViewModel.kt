package movil.ratemypc.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    fun onTwoFactorChange(enabled: Boolean) {
        _uiState.update { it.copy(twoFactorEnabled = enabled) }
    }

    fun onPushNotificationsChange(enabled: Boolean) {
        _uiState.update { it.copy(pushNotificationsEnabled = enabled) }
    }

    fun onEmailNotificationsChange(enabled: Boolean) {
        _uiState.update { it.copy(emailNotificationsEnabled = enabled) }
    }

    fun onProfileVisibleChange(enabled: Boolean) {
        _uiState.update { it.copy(profileVisible = enabled) }
    }

    fun onFollowersAllowedChange(enabled: Boolean) {
        _uiState.update { it.copy(followersAllowed = enabled) }
    }

    fun onAvatarChange() {
        _uiState.update { it.copy(avatarChanged = !it.avatarChanged) }
    }

    fun setShowDeleteDialog(show: Boolean) {
        _uiState.update { it.copy(showDeleteDialog = show) }
    }
}
