package movil.ratemypc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import movil.ratemypc.ui.navigation.RateMyPcBottomBar
import movil.ratemypc.ui.navigation.Screen
import movil.ratemypc.ui.screens.RateMyPcNavHost
import movil.ratemypc.ui.theme.RateMyPcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RateMyPcTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf(
                    Screen.FeedHome.route,
                    Screen.Favoritos.route,
                    Screen.Perfil.route
                )

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) RateMyPcBottomBar(navController)
                    }
                ) { innerPadding ->
                    RateMyPcNavHost(
                        navController = navController,
                        isLoggedIn    = false,
                        modifier      = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
