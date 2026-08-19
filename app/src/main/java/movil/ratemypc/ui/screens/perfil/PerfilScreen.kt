package movil.ratemypc.ui.screens.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.ui.screens.perfil.PerfilComponents.Avatar
import movil.ratemypc.ui.screens.perfil.PerfilComponents.Banner
import movil.ratemypc.ui.screens.perfil.PerfilComponents.EditarPerfil
import movil.ratemypc.ui.screens.perfil.PerfilComponents.InfoPerfil
import movil.ratemypc.ui.screens.perfil.PerfilComponents.TabPerfil

@Composable
fun PerfilScreen() {

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {

                    Banner()

                    Avatar()

                    EditarPerfil()
                }
            }

            item {
                InfoPerfil()
            }

            item{
                TabPerfil()
            }

        }
    }
}

@Preview
@Composable
fun PerfilScreenComposable(){
    PerfilScreen()
}
