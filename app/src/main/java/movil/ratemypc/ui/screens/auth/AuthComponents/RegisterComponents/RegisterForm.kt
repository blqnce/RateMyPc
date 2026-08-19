package movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import movil.ratemypc.ui.screens.auth.AuthComponents.PasswordStrengthIndicator

@Composable
fun RegisterForm(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmError: String?,
    confirmVisible: Boolean,
    onToggleConfirmVisibility: () -> Unit,
    isLoading: Boolean,
    onRegisterClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value         = name,
        onValueChange = onNameChange,
        label         = { Text("Nombre completo") },
        leadingIcon   = { Icon(Icons.Outlined.Person, contentDescription = null) },
        isError       = nameError != null,
        supportingText = nameError?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction    = ImeAction.Next,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth(),
        shape      = MaterialTheme.shapes.small
    )

    Spacer(Modifier.height(12.dp))

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
                    imageVector        = if (passwordVisible) Icons.Outlined.VisibilityOff
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
            imeAction    = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth(),
        shape      = MaterialTheme.shapes.small
    )

    // Indicador de fortaleza
    if (password.isNotEmpty()) {
        Spacer(Modifier.height(6.dp))
        PasswordStrengthIndicator(password = password)
    }

    Spacer(Modifier.height(12.dp))

    // ── Confirmar contraseña ──────────────────────────────────
    OutlinedTextField(
        value         = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        label         = { Text("Confirmar contraseña") },
        leadingIcon   = { Icon(Icons.Outlined.LockOpen, contentDescription = null) },
        trailingIcon  = {
            IconButton(onClick = onToggleConfirmVisibility) {
                Icon(
                    imageVector        = if (confirmVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                    contentDescription = if (confirmVisible) "Ocultar" else "Mostrar"
                )
            }
        },
        visualTransformation = if (confirmVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        isError        = confirmError != null,
        supportingText = confirmError?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction    = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus(); onRegisterClick() }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth(),
        shape      = MaterialTheme.shapes.small
    )

    Spacer(Modifier.height(28.dp))

    // ── Botón principal ───────────────────────────────────────
    Button(
        onClick  = onRegisterClick,
        enabled  = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.small
    ) {
        AnimatedContent(
            targetState = isLoading,
            label       = "register_button"
        ) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    modifier    = Modifier.size(22.dp),
                    color       = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text  = "Crear cuenta",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}