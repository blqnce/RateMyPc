package movil.ratemypc.ui.screens.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.Componente
import movil.ratemypc.data.Resena
import movil.ratemypc.ui.screens.reviews.ReviewComponents.RatingDistribution
import movil.ratemypc.ui.screens.reviews.ReviewComponents.ReviewFilter
import movil.ratemypc.ui.screens.reviews.ReviewComponents.ReviewHeader
import movil.ratemypc.ui.screens.reviews.ReviewComponents.ReviewItemCard
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun ReviewComponenteScreen(
    componente: Componente,
    resenas: List<Resena>,
    onBack: () -> Unit,
    onWriteReview: () -> Unit
) {
    var selectedSource by remember { mutableStateOf("All") }

    val filteredResenas = remember(selectedSource, resenas) {
        if (selectedSource == "All") resenas
        else resenas.filter { it.fuente == selectedSource }
    }

    val distribution = remember(resenas) {
        if (resenas.isEmpty()) emptyMap<Int, Float>()
        else {
            val counts = resenas.groupingBy { it.calificacion }.eachCount()
            counts.mapValues { it.value.toFloat() / resenas.size }
        }
    }

    ReviewComponenteContent(
        componente = componente,
        resenas = filteredResenas,
        selectedSource = selectedSource,
        onSourceChange = { selectedSource = it },
        distribution = distribution,
        totalReviews = resenas.size,
        onBack = onBack,
        onWriteReview = onWriteReview
    )
}

@Composable
fun ReviewComponenteContent(
    componente: Componente,
    resenas: List<Resena>,
    selectedSource: String,
    onSourceChange: (String) -> Unit,
    distribution: Map<Int, Float>,
    totalReviews: Int,
    onBack: () -> Unit,
    onWriteReview: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            ReviewHeader(
                componente = componente,
                onBack = onBack
            )
        }

        item {
            ReviewFilter(
                selectedSource = selectedSource,
                onSourceChange = onSourceChange
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            RatingDistribution(
                totalReviews = totalReviews,
                distribution = distribution
            )
        }

        item {
            Button(
                onClick = onWriteReview,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Escribir una reseña")
            }
        }

        items(resenas) { resena ->
            ReviewItemCard(resena = resena)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewComponenteScreenPreview() {
    val dummyComponente = Componente(
        id = "1",
        nombre = "GeForce RTX 4080 Super",
        marca = "NVIDIA",
        costo = 999.99f,
        consumoEnergetico = 320f,
        fechaLanzamiento = "Jan 2024",
        subCategoria = "GPU",
        promedioCalificacion = 4.7f,
        totalResenas = 1284
    )

    val dummyResenas = listOf(
        Resena(
            id = "1",
            componenteId = "1",
            nombreUsuario = "TechWizard92",
            fecha = "Mar 12, 2024",
            calificacion = 5,
            fuente = "Newegg",
            comentario = "Absolutely destroys 4K gaming. Temps stay cool even under heavy load. Worth every penny for the performance uplift.",
            esCompraVerificada = true
        ),
        Resena(
            id = "2",
            componenteId = "1",
            nombreUsuario = "PCBuilder_Mike",
            fecha = "Feb 28, 2024",
            calificacion = 4,
            fuente = "Amazon",
            comentario = "Great card, runs hot but performs great. Needed better cooling solution in my case. Drivers are solid.",
            esCompraVerificada = true
        ),
        Resena(
            id = "3",
            componenteId = "1",
            nombreUsuario = "GamerDad_TX",
            fecha = "Feb 01, 2024",
            calificacion = 5,
            fuente = "Micro Center",
            comentario = "Picked this up at Micro Center on release day. Incredible deal compared to the 4090. Handles everything I throw at it.",
            esCompraVerificada = false
        )
    )

    MaterialTheme {
        RateMyPcTheme(darkTheme = true) {
            ReviewComponenteScreen(
                componente = dummyComponente,
                resenas = dummyResenas,
                onBack = {},
                onWriteReview = {}
            )
        }
    }
}
