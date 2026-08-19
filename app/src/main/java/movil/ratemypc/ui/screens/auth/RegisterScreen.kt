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
fun RegisterScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RegisterScreenContent()
    }
}

@Composable
fun RegisterScreenContent(){

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

        RegisterForm()

        Spacer(Modifier.height(24.dp))

        Divider()

        Spacer(Modifier.height(32.dp))

        // ── Ir a login ────────────────────────────────────────────
        ToLogin()

        Spacer(Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun RegisterScreenComposable(){
    RegisterScreen()
}