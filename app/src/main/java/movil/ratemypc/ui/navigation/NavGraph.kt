package movil.ratemypc.ui.navigation

sealed class Screen(val route: String) {

    object Login       : Screen("login")
    object Register    : Screen("register")
    object FeedHome : Screen("feed")
    object Favoritos : Screen("favoritos")
    object Compatibilidad : Screen("compatibilidad")
    object Review : Screen("review/{componenteId}") {
        fun createRoute(componenteId: String) = "review/$componenteId"
    }
    object Detalle : Screen("detalle/{componenteId}") {
        fun createRoute(componenteId: String) = "detalle/$componenteId"
    }
    object Perfil  : Screen("perfil")
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
    object WriteReview : Screen("writeReview/{componenteId}") {
        fun createRoute(componenteId: String) = "writeReview/$componenteId"
    }
}