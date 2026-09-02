package movil.ratemypc.ui.screens.settings

data class SettingsState(
    val twoFactorEnabled: Boolean = false,
    val pushNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = false,
    val profileVisible: Boolean = true,
    val followersAllowed: Boolean = true,
    val avatarChanged: Boolean = false,
    val showDeleteDialog: Boolean = false
)
