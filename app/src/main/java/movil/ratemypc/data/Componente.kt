package movil.ratemypc.data

data class Componente(
    val id: String,
    val nombre: String,
    val imageUrl: String? = null,   // ← URI de la foto tomada/seleccionada
    val costo: Float,
    val consumoEnergetico: Float,
    val fechaLanzamiento: String,
    // val subCategoria: Subcategoria,
    // De momento manejamos subcategoria como un string para mostrar un ejemplo de la filtracion por etiquetas
    val subCategoria: String,
    val marca: String,
    val promedioCalificacion: Float,
    val totalResenas: Int

    )