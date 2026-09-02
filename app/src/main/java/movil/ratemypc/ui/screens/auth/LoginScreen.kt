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
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.LoginForm
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.LoginHeader
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.ToRegister

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    viewModel: LoginViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoginScreenContent(
            email = uiState.email,
            onEmailChange = { viewModel.onEmailChange(it) },
            emailError = uiState.emailError,
            password = uiState.password,
            onPasswordChange = { viewModel.onPasswordChange(it) },
            passwordError = uiState.passwordError,
            passwordVisible = uiState.passwordVisible,
            onTogglePasswordVisibility = { viewModel.onTogglePasswordVisibility() },
            isLoading = uiState.isLoading,
            onLoginClick = { viewModel.onLoginClick(onLoginSuccess) },
            onGoToRegister = onGoToRegister,
            scrollState = scrollState
        )
    }
}

@Composable
fun LoginScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isLoading: Boolean,
    onLoginClick: () -> Unit,
    onGoToRegister: () -> Unit,
    scrollState: ScrollState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()

        Spacer(Modifier.height(48.dp))

        LoginForm(
            email = email,
            onEmailChange = onEmailChange,
            emailError = emailError,
            password = password,
            onPasswordChange = onPasswordChange,
            passwordError = passwordError,
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            isLoading = isLoading,
            onLoginClick = onLoginClick
        )

        Spacer(Modifier.height(24.dp))

        Divider()

        Spacer(Modifier.height(32.dp))

        ToRegister(onRegisterClick = onGoToRegister)

        Spacer(Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun LoginScreenComposable(){
    LoginScreen(onLoginSuccess = {}, onGoToRegister = {}, viewModel = LoginViewModel())
}
