package movil.ratemypc.ui.screens.detalle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

class DetalleComponenteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleComponenteState())
    val uiState: StateFlow<DetalleComponenteState> = _uiState.asStateFlow()

    fun loadComponente(componenteId: String) {
        val componente = LocalComponentesProvider.componentes.find { it.id == componenteId }
        val processedComponente = componente?.let { item ->
            val resenas = LocalResenasProvider.resenas.filter { it.componenteId == item.id }
            if (resenas.isNotEmpty()) {
                val promedio = resenas.map { it.calificacion }.average().toFloat()
                val promedioRedondeado = (promedio * 10f).roundToInt() / 10f
                item.copy(
                    promedioCalificacion = promedioRedondeado,
                    totalResenas = resenas.size
                )
            } else {
                item
            }
        }
        _uiState.update { it.copy(componente = processedComponente, isLoading = false) }
    }
}
