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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RegisterForm(){

    var name              by remember { mutableStateOf("") }
    var email             by remember { mutableStateOf("") }
    var password          by remember { mutableStateOf("") }
    var confirmPassword   by remember { mutableStateOf("") }
    var passwordVisible   by remember { mutableStateOf(false) }
    var confirmVisible    by remember { mutableStateOf(false) }
    var isLoading         by remember { mutableStateOf(false) }

    var nameError         by remember { mutableStateOf<String?>(null) }
    var emailError        by remember { mutableStateOf<String?>(null) }
    var passwordError     by remember { mutableStateOf<String?>(null) }
    var confirmError      by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    fun validate(): Boolean {
        nameError     = if (name.isBlank()) "Ingresa tu nombre" else null
        emailError    = if (email.isBlank() || !email.contains("@")) "Ingresa un correo válido" else null
        passwordError = when {
            password.length < 6                -> "Mínimo 6 caracteres"
            !password.any { it.isDigit() }     -> "Debe contener al menos un número"
            !password.any { it.isUpperCase() } -> "Debe contener al menos una mayúscula"
            else                               -> null
        }
        confirmError = if (confirmPassword != password) "Las contraseñas no coinciden" else null
        return listOf(nameError, emailError, passwordError, confirmError).all { it == null }
    }

    fun onRegisterClick() {
        if (!validate()) return

        // Llamar al viewmodel para registrar al usuario

        isLoading = false
        //onRegistered()
    }



    OutlinedTextField(
        value         = name,
        onValueChange = { name = it; nameError = null },
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
        onValueChange = { email = it; emailError = null },
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
        onValueChange = { password = it; passwordError = null },
        label         = { Text("Contraseña") },
        leadingIcon   = { Icon(Icons.Outlined.Lock, contentDescription = null) },
        trailingIcon  = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
        onValueChange = { confirmPassword = it; confirmError = null },
        label         = { Text("Confirmar contraseña") },
        leadingIcon   = { Icon(Icons.Outlined.LockOpen, contentDescription = null) },
        trailingIcon  = {
            IconButton(onClick = { confirmVisible = !confirmVisible }) {
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
        onClick  = { onRegisterClick() },
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