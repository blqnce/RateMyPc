package movil.ratemypc.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.ui.screens.settings.SettingsComponents.*
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun SettingsScreen(
    onBack: () -> Unit = {},
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreenContent(
        modifier = modifier,
        onBack = onBack,
        twoFactorEnabled = uiState.twoFactorEnabled,
        onTwoFactorChange = { viewModel.onTwoFactorChange(it) },
        pushNotificationsEnabled = uiState.pushNotificationsEnabled,
        onPushNotificationsChange = { viewModel.onPushNotificationsChange(it) },
        emailNotificationsEnabled = uiState.emailNotificationsEnabled,
        onEmailNotificationsChange = { viewModel.onEmailNotificationsChange(it) },
        profileVisible = uiState.profileVisible,
        onProfileVisibleChange = { viewModel.onProfileVisibleChange(it) },
        followersAllowed = uiState.followersAllowed,
        onFollowersAllowedChange = { viewModel.onFollowersAllowedChange(it) },
        avatarChanged = uiState.avatarChanged,
        onAvatarChange = { viewModel.onAvatarChange() },
        onDeleteAccountClick = { viewModel.setShowDeleteDialog(true) }
    )

    if (uiState.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowDeleteDialog(false) },
            title = { Text("¿Eliminar cuenta?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = { viewModel.setShowDeleteDialog(false) }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setShowDeleteDialog(false) }) {
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
        contentPadding = PaddingValues(
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
        SettingsScreen(viewModel = SettingsViewModel())
    }
}
