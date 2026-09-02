package movil.ratemypc.ui.screens.favoritos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider
import movil.ratemypc.data.local.LocalResenasProvider
import kotlin.math.roundToInt

class FavoritosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritosState())
    val uiState: StateFlow<FavoritosState> = _uiState.asStateFlow()

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
        // Assuming we start with an empty or default set of favorites if not persisted
        // But for this local exercise, we'll just use them as they are
        _uiState.update { it.copy(favoritos = initialComponentes.filter { it.isFavorite }) }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun toggleFavorite(componenteId: String) {
        _uiState.update { state ->
            val updatedFavoritos = state.favoritos.filter { it.id != componenteId }
            state.copy(favoritos = updatedFavoritos)
        }
    }
}
