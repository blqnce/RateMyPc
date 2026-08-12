package movil.ratemypc.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import movil.ratemypc.data.Componente
import java.util.UUID

class ComponenteViewModel : ViewModel() {

    private val _componentes = MutableStateFlow<List<Componente>>(listOf(

        Componente(
            id = UUID.randomUUID().toString(),
            nombre = "Tarjeta gráfica GeForce RTX 5060",
            imageUrl = "https://m.media-amazon.com/images/I/71ii5ow8slL._AC_SL1500_.jpg",
            costo = 1000f,
            consumoEnergetico = 450f,
            fechaLanzamiento = "2025/05/18",
            subCategoria = "GPU",
            marca = "GYGABITE",
            promedioCalificacion = 4f,
            totalResenas = 20
        ),
        Componente(
            id = UUID.randomUUID().toString(),
            nombre = "Procesador de escritorio Core™ i7-12700KF",
            imageUrl = "https://m.media-amazon.com/images/I/51AqEkc2BuL._AC_SL1000_.jpg",
            costo = 250.9f,
            consumoEnergetico = 120f,
            fechaLanzamiento = "2025/07/2",
            subCategoria = "CPU",
            marca = "INTEL",
            promedioCalificacion = 5f,
            totalResenas = 60
        )
    ))
    val componentes: StateFlow<List<Componente>> = _componentes.asStateFlow()

    fun addComponente(nombre: String, iamgeUrl: Uri?, costo: Float,consumoEnergetico: Float, fechaLanzamiento: String, subCategoria: String,marca: String,promedioCalificacion: Float, totalResenas: Int) {
        val new = Componente(
            id = UUID.randomUUID().toString(),
            nombre = nombre,
            imageUrl = iamgeUrl?.toString(),
            costo = costo,
            consumoEnergetico = consumoEnergetico,
            fechaLanzamiento = fechaLanzamiento,
            subCategoria = subCategoria,
            marca = marca,
            promedioCalificacion = promedioCalificacion,
            totalResenas = totalResenas
        )
        _componentes.value = _componentes.value + new
    }
}