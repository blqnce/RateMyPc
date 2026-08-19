package movil.ratemypc.ui.screens.auth.AuthComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import movil.ratemypc.R

@Composable
fun PasswordStrengthIndicator(password: String) {
    val strength = when {
        password.length >= 8
                && password.any { it.isDigit() }
                && password.any { it.isUpperCase() }
                && password.any { !it.isLetterOrDigit() } -> 3  // Fuerte
        password.length >= 6
                && (password.any { it.isDigit() }
                || password.any { it.isUpperCase() })     -> 2  // Media
        else                                          -> 1  // Débil
    }

    val (label, color) = when (strength) {
        3    -> "Contraseña fuerte" to MaterialTheme.colorScheme.secondary
        2    -> "Contraseña media"  to MaterialTheme.colorScheme.tertiary
        else -> "Contraseña débil"  to MaterialTheme.colorScheme.error
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier             = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            color = if (index < strength) color
                            else MaterialTheme.colorScheme.outlineVariant,
                            shape = MaterialTheme.shapes.extraSmall
                        )
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}