package movil.ratemypc.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.ui.navigation.Screen
import movil.ratemypc.ui.screens.auth.LoginScreen
import movil.ratemypc.ui.screens.auth.RegisterScreen
import movil.ratemypc.ui.screens.feed.FeedHomeScreen
import movil.ratemypc.ui.screens.favoritos.FavoritosScreen
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadScreen
import movil.ratemypc.ui.screens.perfil.PerfilScreen
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun RateMyPcNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    val ComponenteViewModel: ComponenteViewModel = viewModel()
    val startDestination = when {
        !isLoggedIn             -> Screen.Login.route
        else                    -> Screen.FeedHome.route
    }

    NavHost(
        navController    = navController,
        startDestination = startDestination,
        modifier         = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen()
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistered = { navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }},
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FeedHome.route) {
            FeedHomeScreen(
                // Añadir pantalla de un componente especifico
            )
        }

        composable(Screen.Favoritos.route) {
            FavoritosScreen(
                viewModel = ComponenteViewModel,
                onOpenCompatibility = {
                    navController.navigate(Screen.Compatibilidad.route)
                }
            )
        }

        composable(Screen.Compatibilidad.route) {
            CompatibilidadScreen(viewModel = ComponenteViewModel)
        }


        composable(Screen.Perfil.route) {
            PerfilScreen()
        }

    }
}
