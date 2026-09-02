package movil.ratemypc.ui.screens.detalle

import movil.ratemypc.data.ComponenteItem

data class DetalleComponenteState(
    val componente: ComponenteItem? = null,
    val isLoading: Boolean = false
)
