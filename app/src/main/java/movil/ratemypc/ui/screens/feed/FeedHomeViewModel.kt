package movil.ratemypc.ui.screens.feed

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

// ViewModel para la pantalla principal de Feed.
// Carga y gestiona la lista de componentes de PC, incluyendo sus calificaciones promedio y estado de favoritos.
@HiltViewModel
class FeedHomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(FeedHomeState())
    val uiState: StateFlow<FeedHomeState> = _uiState.asStateFlow()

    init {
        loadInitialComponentes()
    }

    // Carga la lista inicial de componentes y calcula sus estadísticas basadas en las reseñas locales.
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

    // Cambia la categoría de componentes seleccionada para filtrar el feed.
    fun onCategoryChange(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    // Actualiza la consulta de búsqueda para filtrar la lista de componentes por nombre.
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // Alterna el estado de favorito de un componente específico.
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
