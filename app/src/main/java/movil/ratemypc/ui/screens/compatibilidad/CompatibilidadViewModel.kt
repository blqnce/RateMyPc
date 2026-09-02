package movil.ratemypc.ui.screens.compatibilidad

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

class CompatibilidadViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CompatibilidadState())
    val uiState: StateFlow<CompatibilidadState> = _uiState.asStateFlow()

    init {
        loadInitialComponentes()
    }

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

    fun onAnalyzeClick() {
        _uiState.update { it.copy(analyzed = true) }
    }

    fun setSelectedComponents(components: List<ComponenteItem>) {
        _uiState.update { it.copy(selectedComponents = components) }
    }
}
