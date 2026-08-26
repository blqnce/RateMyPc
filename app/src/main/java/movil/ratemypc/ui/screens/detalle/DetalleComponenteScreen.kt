package movil.ratemypc.ui.screens.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.screens.detalle.DetalleComponents.DetalleHeader
import movil.ratemypc.ui.screens.detalle.DetalleComponents.DetalleInfo
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun DetalleComponenteScreen(
    componenteId: String,
    viewModel: ComponenteViewModel,
    onBack: () -> Unit
) {
    val componentes by viewModel.componentes.collectAsState()
    val componente = componentes.find { it.id == componenteId }

    if (componente != null) {
        DetalleComponenteContent(
            componente = componente,
            onBack = onBack
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Componente no encontrado")
        }
    }
}

@Composable
fun DetalleComponenteContent(
    componente: Componente,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        DetalleHeader(
            componente = componente,
            onBack = onBack
        )
        DetalleInfo(componente = componente)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* Implementar agregar a build */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Agregar a mi Build")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
