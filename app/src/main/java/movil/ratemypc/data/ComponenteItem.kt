package movil.ratemypc.data

data class ComponenteItem(
    val id: String,
    val nombre: String,
    val imageUrl: String? = null,
    val costo: Float,
    val consumoEnergetico: Float,
    val fechaLanzamiento: String,
    val subCategoria: String,
    val marca: String,
    val promedioCalificacion: Float,
    val totalResenas: Int,
    val isFavorite: Boolean = false
)
