package movil.ratemypc.ui.screens.reviews

import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.data.ResenaItem

data class ReviewComponenteState(
    val componente: ComponenteItem? = null,
    val resenas: List<ResenaItem> = emptyList(),
    val selectedSource: String = "All"
) {
    val filteredResenas: List<ResenaItem>
        get() = if (selectedSource == "All") resenas
        else resenas.filter { it.fuente == selectedSource }

    val distribution: Map<Int, Float>
        get() = if (resenas.isEmpty()) emptyMap()
        else {
            val counts = resenas.groupingBy { it.calificacion }.eachCount()
            counts.mapValues { it.value.toFloat() / resenas.size }
        }

    val totalReviews: Int get() = resenas.size
}
