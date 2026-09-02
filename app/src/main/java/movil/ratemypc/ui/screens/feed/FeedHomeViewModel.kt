package movil.ratemypc.ui.screens.feed

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

class FeedHomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FeedHomeState())
    val uiState: StateFlow<FeedHomeState> = _uiState.asStateFlow()

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
        _uiState.update { it.copy(componentes = initialComponentes) }
    }

    fun onCategoryChange(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleFavorite(componenteId: String) {
        _uiState.update { state ->
            val updatedList = state.componentes.map { item ->
                if (item.id == componenteId) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
            state.copy(componentes = updatedList)
        }
    }
}
