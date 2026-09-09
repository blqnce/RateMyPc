package movil.ratemypc.ui.screens.detalle

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

import javax.inject.Inject

// ViewModel para la pantalla de detalles de un componente.
// Obtiene la información técnica y las calificaciones actualizadas de un producto específico.
@HiltViewModel
class DetalleComponenteViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleComponenteState())
    val uiState: StateFlow<DetalleComponenteState> = _uiState.asStateFlow()

    // Busca y carga los detalles de un componente por su ID, incluyendo el cálculo de su calificación promedio.
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
