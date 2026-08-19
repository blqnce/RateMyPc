package movil.ratemypc.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.ui.screens.settings.SettingsComponents.DangerZoneCard
import movil.ratemypc.ui.screens.settings.SettingsComponents.ProfileSettingsCard
import movil.ratemypc.ui.screens.settings.SettingsComponents.SettingsHeader
import movil.ratemypc.ui.screens.settings.SettingsComponents.SettingsOptionRow
import movil.ratemypc.ui.screens.settings.SettingsComponents.SettingsSection
import movil.ratemypc.ui.screens.settings.SettingsComponents.SettingsSwitchRow
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun SettingsScreen(
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var twoFactorEnabled by remember { mutableStateOf(false) }
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var emailNotificationsEnabled by remember { mutableStateOf(false) }
    var profileVisible by remember { mutableStateOf(true) }
    var followersAllowed by remember { mutableStateOf(true) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var avatarChanged by remember { mutableStateOf(false) }

    SettingsScreenContent(
        modifier = modifier,
        onBack = onBack,
        twoFactorEnabled = twoFactorEnabled,
        onTwoFactorChange = { twoFactorEnabled = it },
        pushNotificationsEnabled = pushNotificationsEnabled,
        onPushNotificationsChange = { pushNotificationsEnabled = it },
        emailNotificationsEnabled = emailNotificationsEnabled,
        onEmailNotificationsChange = { emailNotificationsEnabled = it },
        profileVisible = profileVisible,
        onProfileVisibleChange = { profileVisible = it },
        followersAllowed = followersAllowed,
        onFollowersAllowedChange = { followersAllowed = it },
        avatarChanged = avatarChanged,
        onAvatarChange = { avatarChanged = !avatarChanged },
        onDeleteAccountClick = { showDeleteDialog = true }
    )

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar cuenta?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    twoFactorEnabled: Boolean,
    onTwoFactorChange: (Boolean) -> Unit,
    pushNotificationsEnabled: Boolean,
    onPushNotificationsChange: (Boolean) -> Unit,
    emailNotificationsEnabled: Boolean,
    onEmailNotificationsChange: (Boolean) -> Unit,
    profileVisible: Boolean,
    onProfileVisibleChange: (Boolean) -> Unit,
    followersAllowed: Boolean,
    onFollowersAllowedChange: (Boolean) -> Unit,
    avatarChanged: Boolean,
    onAvatarChange: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SettingsHeader(onBack = onBack)
        }

        item {
            ProfileSettingsCard(
                avatarChanged = avatarChanged,
                onChangeAvatar = onAvatarChange,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SettingsSection(title = "Account") {
                SettingsOptionRow(Icons.Outlined.Email, "Cambiar correo", "techwizard92@email.com")
                SettingsOptionRow(Icons.Outlined.Lock, "Cambiar contraseña", "Actualiza tu contraseña")
                SettingsSwitchRow(
                    icon = Icons.Outlined.Shield,
                    title = "Autenticación de dos factores",
                    checked = twoFactorEnabled,
                    onCheckedChange = onTwoFactorChange
                )
            }
        }

        item {
            SettingsSection(title = "Notifications") {
                SettingsSwitchRow(
                    icon = Icons.Outlined.Notifications,
                    title = "Notificaciones push",
                    checked = pushNotificationsEnabled,
                    onCheckedChange = onPushNotificationsChange
                )
                SettingsSwitchRow(
                    icon = Icons.Outlined.Email,
                    title = "Notificaciones por correo",
                    checked = emailNotificationsEnabled,
                    onCheckedChange = onEmailNotificationsChange
                )
            }
        }

        item {
            SettingsSection(title = "Privacy") {
                SettingsSwitchRow(
                    icon = Icons.Outlined.Visibility,
                    title = "Perfil visible",
                    checked = profileVisible,
                    onCheckedChange = onProfileVisibleChange
                )
                SettingsSwitchRow(
                    icon = Icons.Outlined.Group,
                    title = "Permitir seguidores",
                    checked = followersAllowed,
                    onCheckedChange = onFollowersAllowedChange
                )
            }
        }

        item {
            DangerZoneCard(onDeleteAccount = onDeleteAccountClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    RateMyPcTheme {
        SettingsScreen()
    }
}
