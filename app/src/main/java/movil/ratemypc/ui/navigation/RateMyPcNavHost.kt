package movil.ratemypc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.ui.screens.auth.LoginScreen
import movil.ratemypc.ui.screens.auth.RegisterScreen
import movil.ratemypc.ui.screens.feed.FeedHomeScreen
import movil.ratemypc.ui.screens.favoritos.FavoritosScreen
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadScreen
import movil.ratemypc.ui.screens.perfil.PerfilScreen
import movil.ratemypc.ui.screens.reviews.ReviewComponenteScreen
import movil.ratemypc.ui.screens.detalle.DetalleComponenteScreen
import movil.ratemypc.ui.screens.settings.SettingsScreen
import movil.ratemypc.ui.screens.notifications.NotificationsScreen
import movil.ratemypc.ui.screens.review.WriteReviewScreen
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun RateMyPcNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    val componenteViewModel: ComponenteViewModel = viewModel()
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
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.FeedHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
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
                viewModel = componenteViewModel,
                onOpenReview = { componenteId ->
                    navController.navigate(Screen.Review.createRoute(componenteId))
                },
                onOpenDetail = { componenteId ->
                    navController.navigate(Screen.Detalle.createRoute(componenteId))
                }
            )
        }

        composable(Screen.Favoritos.route) {
            FavoritosScreen(
                viewModel = componenteViewModel,
                onOpenCompatibility = {
                    navController.navigate(Screen.Compatibilidad.route)
                }
            )
        }

        composable(Screen.Compatibilidad.route) {
            CompatibilidadScreen(
                viewModel = componenteViewModel,
                onBack = { navController.popBackStack() }
            )
        }


        composable(Screen.Perfil.route) {
            PerfilScreen(
                onOpenSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Review.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            // En una app real, buscaríamos el componente por ID
            // Aquí usamos uno temporal para demostrar la pantalla
            val componentes by componenteViewModel.componentes.collectAsState()
            val componente = componentes.find { it.id == componenteId } ?: componentes.first()
            
            ReviewComponenteScreen(
                componente = componente,
                resenas = emptyList(), // TODO: Implementar carga de reseñas
                onBack = { navController.popBackStack() },
                onWriteReview = { navController.navigate(Screen.WriteReview.createRoute(componenteId)) }
            )
        }

        composable(Screen.WriteReview.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            val componentes by componenteViewModel.componentes.collectAsState()
            val componente = componentes.find { it.id == componenteId } ?: componentes.first()

            WriteReviewScreen(
                component = componente,
                onBack = { navController.popBackStack() },
                onSubmit = { _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Detalle.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            DetalleComponenteScreen(
                componenteId = componenteId,
                viewModel = componenteViewModel,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
