package movil.ratemypc.ui.screens.review

import movil.ratemypc.data.ComponenteItem

data class WriteReviewState(
    val component: ComponenteItem? = null,
    val rating: Int = 0,
    val reviewText: String = "",
    val selectedShop: String = "Amazon",
    val imageUrl: String? = null,
    val shops: List<String> = listOf("Amazon", "Newegg", "Micro Center", "B&H")
) {
    val isValid: Boolean get() = rating > 0 && reviewText.isNotBlank() && selectedShop.isNotBlank()
}
