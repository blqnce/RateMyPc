package movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isLoading: Boolean,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    // ── Email ─────────────────────────────────────────────────
    OutlinedTextField(
        value         = email,
        onValueChange = onEmailChange,
        label         = { Text("Correo electrónico") },
        leadingIcon   = { Icon(Icons.Outlined.Email, contentDescription = null) },
        isError       = emailError != null,
        supportingText = emailError?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction    = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth(),
        shape      = MaterialTheme.shapes.small
    )

    Spacer(Modifier.height(12.dp))

    // ── Contraseña ────────────────────────────────────────────
    OutlinedTextField(
        value         = password,
        onValueChange = onPasswordChange,
        label         = { Text("Contraseña") },
        leadingIcon   = { Icon(Icons.Outlined.Lock, contentDescription = null) },
        trailingIcon  = {
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        isError        = passwordError != null,
        supportingText = passwordError?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction    = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus(); onLoginClick() }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth(),
        shape      = MaterialTheme.shapes.small
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick  = {
                // Recuperar contraseña
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                text  = "¿Olvidaste tu contraseña?",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    Spacer(Modifier.height(24.dp))

    Button(
        onClick  = onLoginClick,
        enabled  = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.small
    ) {
        AnimatedContent(
            targetState = isLoading,
            label       = "login_button"
        ) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color    = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text  = "Ingresar",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
