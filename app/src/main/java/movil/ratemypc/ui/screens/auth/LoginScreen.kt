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
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.LoginForm
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.LoginHeader
import movil.ratemypc.ui.screens.auth.AuthComponents.LoginComponents.ToRegister

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoginScreenContent()
    }
}

@Composable
fun LoginScreenContent(){

    val scrollState  = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()

        Spacer(Modifier.height(48.dp))

        LoginForm()

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



