package movil.ratemypc.data

data class Resena(
    val id: String,
    val componenteId: String,
    val nombreUsuario: String,
    val fecha: String,
    val calificacion: Int,
    val fuente: String, // Newegg, Amazon, etc.
    val comentario: String,
    val esCompraVerificada: Boolean
)