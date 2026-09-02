package movil.ratemypc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import movil.ratemypc.ui.screens.auth.LoginScreen
import movil.ratemypc.ui.screens.auth.LoginViewModel
import movil.ratemypc.ui.screens.auth.RegisterScreen
import movil.ratemypc.ui.screens.auth.RegisterViewModel
import movil.ratemypc.ui.screens.feed.FeedHomeScreen
import movil.ratemypc.ui.screens.feed.FeedHomeViewModel
import movil.ratemypc.ui.screens.favoritos.FavoritosScreen
import movil.ratemypc.ui.screens.favoritos.FavoritosViewModel
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadScreen
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadViewModel
import movil.ratemypc.ui.screens.perfil.PerfilScreen
import movil.ratemypc.ui.screens.perfil.PerfilViewModel
import movil.ratemypc.ui.screens.reviews.ReviewComponenteScreen
import movil.ratemypc.ui.screens.reviews.ReviewComponenteViewModel
import movil.ratemypc.ui.screens.detalle.DetalleComponenteScreen
import movil.ratemypc.ui.screens.detalle.DetalleComponenteViewModel
import movil.ratemypc.ui.screens.settings.SettingsScreen
import movil.ratemypc.ui.screens.settings.SettingsViewModel
import movil.ratemypc.ui.screens.notifications.NotificationsScreen
import movil.ratemypc.ui.screens.notifications.NotificationsViewModel
import movil.ratemypc.ui.screens.review.WriteReviewScreen
import movil.ratemypc.ui.screens.review.WriteReviewViewModel

@Composable
fun RateMyPcNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
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
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.FeedHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                viewModel = loginViewModel
            )
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                onRegistered = { navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }},
                onBack = { navController.popBackStack() },
                viewModel = registerViewModel
            )
        }

        composable(Screen.FeedHome.route) {
            val feedHomeViewModel: FeedHomeViewModel = viewModel()
            FeedHomeScreen(
                feedHomeViewModel = feedHomeViewModel,
                onOpenReview = { componenteId ->
                    navController.navigate(Screen.Review.createRoute(componenteId))
                },
                onOpenDetail = { componenteId ->
                    navController.navigate(Screen.Detalle.createRoute(componenteId))
                }
            )
        }

        composable(Screen.Favoritos.route) {
            val favoritosViewModel: FavoritosViewModel = viewModel()
            FavoritosScreen(
                favoritosViewModel = favoritosViewModel,
                onOpenCompatibility = {
                    navController.navigate(Screen.Compatibilidad.route)
                }
            )
        }

        composable(Screen.Compatibilidad.route) {
            val compatibilidadViewModel: CompatibilidadViewModel = viewModel()
            CompatibilidadScreen(
                compatibilidadViewModel = compatibilidadViewModel,
                onBack = { navController.popBackStack() }
            )
        }


        composable(Screen.Perfil.route) {
            val perfilViewModel: PerfilViewModel = viewModel()
            PerfilScreen(
                onOpenSettings = { navController.navigate(Screen.Settings.route) },
                viewModel = perfilViewModel
            )
        }

        composable(Screen.Notifications.route) {
            val notificationsViewModel: NotificationsViewModel = viewModel()
            NotificationsScreen(
                navController = navController,
                viewModel = notificationsViewModel
            )
        }

        composable(Screen.Settings.route) {
            val settingsViewModel: SettingsViewModel = viewModel()
            SettingsScreen(
                onBack = { navController.popBackStack() },
                viewModel = settingsViewModel
            )
        }

        composable(Screen.Review.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            val reviewViewModel: ReviewComponenteViewModel = viewModel()
            ReviewComponenteScreen(
                componenteId = componenteId,
                onBack = { navController.popBackStack() },
                onWriteReview = { navController.navigate(Screen.WriteReview.createRoute(componenteId)) },
                viewModel = reviewViewModel
            )
        }

        composable(Screen.WriteReview.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            val writeReviewViewModel: WriteReviewViewModel = viewModel()
            WriteReviewScreen(
                componenteId = componenteId,
                onBack = { navController.popBackStack() },
                onSubmit = { _, _, _, _ ->
                    navController.popBackStack()
                },
                viewModel = writeReviewViewModel
            )
        }

        composable(Screen.Detalle.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            val detalleViewModel: DetalleComponenteViewModel = viewModel()
            DetalleComponenteScreen(
                componenteId = componenteId,
                detalleViewModel = detalleViewModel,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
