package movil.ratemypc.ui.screens.reviews

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

class ReviewComponenteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewComponenteState())
    val uiState: StateFlow<ReviewComponenteState> = _uiState.asStateFlow()

    fun loadData(componenteId: String) {
        val componente = LocalComponentesProvider.componentes.find { it.id == componenteId }
        val resenas = LocalResenasProvider.resenas.filter { it.componenteId == componenteId }
        
        val processedComponente = componente?.let { item ->
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
        
        _uiState.update { it.copy(componente = processedComponente, resenas = resenas) }
    }

    fun onSourceChange(source: String) {
        _uiState.update { it.copy(selectedSource = source) }
    }
}
