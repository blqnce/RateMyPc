package movil.ratemypc.ui.screens.review

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import movil.ratemypc.data.local.LocalComponentesProvider

class WriteReviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WriteReviewState())
    val uiState: StateFlow<WriteReviewState> = _uiState.asStateFlow()

    fun loadComponent(componenteId: String) {
        val componente = LocalComponentesProvider.componentes.find { it.id == componenteId }
        _uiState.update { it.copy(component = componente) }
    }

    fun onRatingChange(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    fun onReviewTextChange(text: String) {
        _uiState.update { it.copy(reviewText = text) }
    }

    fun onShopChange(shop: String) {
        _uiState.update { it.copy(selectedShop = shop) }
    }

    fun onImageChange(url: String?) {
        _uiState.update { it.copy(imageUrl = url) }
    }
}
