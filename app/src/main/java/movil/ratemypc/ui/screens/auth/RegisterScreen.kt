package movil.ratemypc.ui.screens.auth

import androidx.compose.foundation.ScrollState
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
import movil.ratemypc.ui.screens.auth.RegisterViewModel
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.RegisterHeader
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.ToLogin

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RegisterScreenContent(
            name = uiState.name,
            onNameChange = { viewModel.onNameChange(it) },
            nameError = uiState.nameError,
            email = uiState.email,
            onEmailChange = { viewModel.onEmailChange(it) },
            emailError = uiState.emailError,
            password = uiState.password,
            onPasswordChange = { viewModel.onPasswordChange(it) },
            passwordError = uiState.passwordError,
            passwordVisible = uiState.passwordVisible,
            onTogglePasswordVisibility = { viewModel.onTogglePasswordVisibility() },
            confirmPassword = uiState.confirmPassword,
            onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
            confirmError = uiState.confirmError,
            confirmVisible = uiState.confirmVisible,
            onToggleConfirmVisibility = { viewModel.onToggleConfirmVisibility() },
            isLoading = uiState.isLoading,
            onRegisterClick = { viewModel.onRegisterClick(onRegistered) },
            scrollState = scrollState,
            onBack = onBack
        )
    }
}

@Composable
fun RegisterScreenContent(
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
    onRegisterClick: () -> Unit,
    scrollState: ScrollState,
    onBack: () -> Unit
){
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
            onNameChange = onNameChange,
            nameError = nameError,
            email = email,
            onEmailChange = onEmailChange,
            emailError = emailError,
            password = password,
            onPasswordChange = onPasswordChange,
            passwordError = passwordError,
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = onConfirmPasswordChange,
            confirmError = confirmError,
            confirmVisible = confirmVisible,
            onToggleConfirmVisibility = onToggleConfirmVisibility,
            isLoading = isLoading,
            onRegisterClick = onRegisterClick
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
    RegisterScreen(onRegistered = {}, onBack = {}, viewModel = RegisterViewModel())
}

@Preview(showBackground = true)
@Composable
fun RegisterHeaderPreview(){
    RegisterHeader()
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview(){
    RegisterForm(
        name = "",
        onNameChange = {},
        nameError = "",
        email = "",
        onEmailChange = {},
        emailError = "",
        password = "",
        onPasswordChange = {},
        passwordError = "",
        passwordVisible = false,
        onTogglePasswordVisibility = {},
        confirmPassword = "",
        onConfirmPasswordChange = {},
        confirmError = "",
        confirmVisible = true,
        onToggleConfirmVisibility = {},
        isLoading = false,
        onRegisterClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ToLoginPreview(){
    ToLogin(
        onLoginClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DividerPreview2(){
    Divider()
}
