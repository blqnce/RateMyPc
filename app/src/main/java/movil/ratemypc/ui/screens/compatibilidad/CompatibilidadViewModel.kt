package movil.ratemypc.ui.screens.compatibilidad

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

import javax.inject.Inject

// ViewModel para la pantalla de Compatibilidad.
// Permite seleccionar componentes y analizar su compatibilidad técnica y rendimiento conjunto.
@HiltViewModel
class CompatibilidadViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CompatibilidadState())
    val uiState: StateFlow<CompatibilidadState> = _uiState.asStateFlow()

    init {
        loadInitialComponentes()
    }

    // Carga componentes de prueba iniciales para la comparación.
    private fun loadInitialComponentes() {
        val initialComponentes = LocalComponentesProvider.componentes.map { componente ->
            val resenas = LocalResenasProvider.resenas.filter { it.componenteId == componente.id }
            if (resenas.isNotEmpty()) {
                val promedio = resenas.map { it.calificacion }.average().toFloat()
                val promedioRedondeado = (promedio * 10f).roundToInt() / 10f
                componente.copy(
                    promedioCalificacion = promedioRedondeado,
                    totalResenas = resenas.size
                )
            } else {
                componente
            }
        }
        setSelectedComponents(initialComponentes.take(2))
    }

    // Activa el estado de análisis de compatibilidad.
    fun onAnalyzeClick() {
        _uiState.update { it.copy(analyzed = true) }
    }

    // Establece la lista de componentes seleccionados para el análisis.
    fun setSelectedComponents(components: List<ComponenteItem>) {
        _uiState.update { it.copy(selectedComponents = components) }
    }
}
