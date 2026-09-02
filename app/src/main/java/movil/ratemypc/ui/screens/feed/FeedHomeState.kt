package movil.ratemypc.ui.screens.feed

import movil.ratemypc.data.ComponenteItem

data class FeedHomeState(
    val selectedCategory: String = "Todo",
    val searchQuery: String = "",
    val componentes: List<ComponenteItem> = emptyList()
) {
    val dynamicCategories: List<String>
        get() = listOf("Todo") + componentes.map { it.subCategoria }.distinct().sorted()

    val filteredComponentes: List<ComponenteItem>
        get() = componentes.filter { componente ->
            val matchesCategory = selectedCategory == "Todo" || componente.subCategoria == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                    componente.nombre.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
}
