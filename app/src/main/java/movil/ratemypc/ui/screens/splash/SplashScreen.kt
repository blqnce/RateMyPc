package movil.ratemypc.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToStart: () -> Unit,
    splashViewModel: SplashViewModel
) {
    val navigateHome by splashViewModel.navigateHome.collectAsState()

    if (navigateHome) {
        navigateToHome()
    } else {
        navigateToStart()
    }
}
