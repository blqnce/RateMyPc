package movil.ratemypc.data

data class ResenaItem(
    val id: String,
    val componenteId: String,
    val nombreUsuario: String,
    val fecha: String,
    val calificacion: Int,
    val fuente: String,
    val comentario: String,
    val esCompraVerificada: Boolean
)
