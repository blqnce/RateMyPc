package movil.ratemypc.ui.screens.favoritos

import movil.ratemypc.data.ComponenteItem

data class FavoritosState(
    val selectedTab: Int = 0,
    val favoritos: List<ComponenteItem> = emptyList()
)
