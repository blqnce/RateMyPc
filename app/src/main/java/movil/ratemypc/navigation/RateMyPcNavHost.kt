package movil.ratemypc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import movil.ratemypc.ui.screens.splash.SplashScreen

@Composable
fun RateMyPcNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Splash.route,
        modifier         = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                splashViewModel = hiltViewModel(),
                navigateToHome = {
                    navController.navigate(Screen.FeedHome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToStart = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.FeedHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistered = {
                    navController.navigate(Screen.FeedHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.FeedHome.route) {
            FeedHomeScreen(
                feedHomeViewModel = hiltViewModel(),
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
                favoritosViewModel = hiltViewModel(),
                onOpenCompatibility = {
                    navController.navigate(Screen.Compatibilidad.route)
                }
            )
        }

        composable(Screen.Compatibilidad.route) {
            CompatibilidadScreen(
                compatibilidadViewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }


        composable(Screen.Perfil.route) {
            PerfilScreen(
                onOpenSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.Review.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            ReviewComponenteScreen(
                componenteId = componenteId,
                onBack = { navController.popBackStack() },
                onWriteReview = { navController.navigate(Screen.WriteReview.createRoute(componenteId)) },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.WriteReview.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            WriteReviewScreen(
                componenteId = componenteId,
                onBack = { navController.popBackStack() },
                onSubmit = { _, _, _, _ ->
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.Detalle.route) { backStackEntry ->
            val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
            DetalleComponenteScreen(
                componenteId = componenteId,
                detalleViewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

    }
}
