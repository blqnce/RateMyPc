package movil.ratemypc.ui.screens.review

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider

import javax.inject.Inject

// ViewModel para la pantalla de creación de reseñas.
// Gestiona el estado de la nueva reseña, incluyendo calificación, texto y tienda de compra.
@HiltViewModel
class WriteReviewViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WriteReviewState())
    val uiState: StateFlow<WriteReviewState> = _uiState.asStateFlow()

    // Busca y carga la información del componente que se va a reseñar.
    fun loadComponent(componenteId: String) {
        val componente = LocalComponentesProvider.componentes.find { it.id == componenteId }
        _uiState.update { it.copy(component = componente) }
    }

    // Actualiza la calificación asignada por el usuario (estrellas).
    fun onRatingChange(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    // Actualiza el contenido del comentario de la reseña.
    fun onReviewTextChange(text: String) {
        _uiState.update { it.copy(reviewText = text) }
    }

    // Actualiza la tienda donde se adquirió el producto.
    fun onShopChange(shop: String) {
        _uiState.update { it.copy(selectedShop = shop) }
    }

    // Gestiona el cambio de la imagen adjunta a la reseña.
    fun onImageChange(url: String?) {
        _uiState.update { it.copy(imageUrl = url) }
    }
}
