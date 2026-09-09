package movil.ratemypc.ui.screens.favoritos

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

// ViewModel para la pantalla de Favoritos.
// Gestiona la lista de componentes que el usuario ha marcado como favoritos.
@HiltViewModel
class FavoritosViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritosState())
    val uiState: StateFlow<FavoritosState> = _uiState.asStateFlow()

    init {
        loadInitialComponentes()
    }

    // Carga y filtra los componentes marcados como favoritos desde los datos locales.
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
        _uiState.update { it.copy(favoritos = initialComponentes.filter { it.isFavorite }) }
    }

    // Actualiza la pestaña seleccionada dentro de la pantalla de favoritos.
    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    // Elimina un componente de la lista de favoritos.
    fun toggleFavorite(componenteId: String) {
        _uiState.update { state ->
            val updatedFavoritos = state.favoritos.filter { it.id != componenteId }
            state.copy(favoritos = updatedFavoritos)
        }
    }
}
