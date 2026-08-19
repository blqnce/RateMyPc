package movil.ratemypc.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.ui.screens.auth.AuthComponents.Divider
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.RegisterForm
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.RegisterHeader
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.ToLogin

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RegisterScreenContent(
            onRegistered = onRegistered,
            onBack       = onBack
        )
    }
}

@Composable
fun RegisterScreenContent(
    onRegistered: () -> Unit,
    onBack: () -> Unit
){

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
        isLoading = true
        // Simular registro
        isLoading = false
        onRegistered()
    }

    val scrollState  = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(56.dp))

        RegisterHeader()

        Spacer(Modifier.height(32.dp))

        RegisterForm(
            name = name,
            onNameChange = { name = it; nameError = null },
            nameError = nameError,
            email = email,
            onEmailChange = { email = it; emailError = null },
            emailError = emailError,
            password = password,
            onPasswordChange = { password = it; passwordError = null },
            passwordError = passwordError,
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it; confirmError = null },
            confirmError = confirmError,
            confirmVisible = confirmVisible,
            onToggleConfirmVisibility = { confirmVisible = !confirmVisible },
            isLoading = isLoading,
            onRegisterClick = { onRegisterClick() }
        )

        Spacer(Modifier.height(24.dp))

        Divider()

        Spacer(Modifier.height(32.dp))

        // ── Ir a login ────────────────────────────────────────────
        ToLogin(onLoginClick = onBack)

        Spacer(Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun RegisterScreenComposable(){
    RegisterScreen(onRegistered = {}, onBack = {})
}