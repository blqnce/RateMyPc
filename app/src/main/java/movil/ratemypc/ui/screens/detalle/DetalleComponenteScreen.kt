package movil.ratemypc.ui.screens.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.ui.screens.detalle.DetalleComponents.DetalleHeader
import movil.ratemypc.ui.screens.detalle.DetalleComponents.DetalleInfo

import androidx.compose.ui.res.stringResource
import movil.ratemypc.R

@Composable
fun DetalleComponenteScreen(
    componenteId: String,
    detalleViewModel: DetalleComponenteViewModel,
    onBack: () -> Unit
) {
    val uiState by detalleViewModel.uiState.collectAsState()

    LaunchedEffect(componenteId) {
        detalleViewModel.loadComponente(componenteId)
    }

    val componente = uiState.componente

    if (componente != null) {
        DetalleComponenteContent(
            componente = componente,
            onBack = onBack
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(stringResource(R.string.componente_no_encontrado))
            }
        }
    }
}

@Composable
fun DetalleComponenteContent(
    componente: ComponenteItem,
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
            Text(stringResource(R.string.agregar_a_mi_build))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
