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
fun LoginScreen() {
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading       by remember { mutableStateOf(false) }
    var emailError      by remember { mutableStateOf<String?>(null) }
    var passwordError   by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        emailError    = if (email.isBlank() || !email.contains("@")) "Ingresa un correo válido" else null
        passwordError = if (password.length < 6) "Mínimo 6 caracteres" else null
        return emailError == null && passwordError == null
    }

    fun onLoginClick() {
        if (!validate()) return
        isLoading = true
    }

    val scrollState  = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoginScreenContent(
            email = email,
            onEmailChange = { email = it; emailError = null },
            emailError = emailError,
            password = password,
            onPasswordChange = { password = it; passwordError = null },
            passwordError = passwordError,
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
            isLoading = isLoading,
            onLoginClick = { onLoginClick() },
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

        ToRegister()

        Spacer(Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun LoginScreenComposable(){
    LoginScreen()
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview(){
    LoginForm(
        email = "",
        onEmailChange = {},
        emailError = "",
        password = "",
        onPasswordChange = {},
        passwordError = "",
        passwordVisible = false,
        onTogglePasswordVisibility = {},
        isLoading = false,
        onLoginClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoginHeaderPreview(){
    LoginHeader()
}

@Preview(showBackground = true)
@Composable
fun ToRegisterPreview(){
    ToRegister()
}

@Preview(showBackground = true)
@Composable
fun DividerPreview(){
    Divider()
}
