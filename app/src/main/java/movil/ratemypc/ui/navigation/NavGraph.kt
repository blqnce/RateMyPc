package movil.ratemypc.ui.navigation

sealed class Screen(val route: String) {

    // Auth
    object Login       : Screen("login")
    object Register    : Screen("register")

    // Feed
    object FeedHome : Screen("feed")

    // Perfil
    object Perfil  : Screen("perfil")
}