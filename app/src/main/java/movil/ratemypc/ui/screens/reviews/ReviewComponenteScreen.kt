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
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.data.ResenaItem
import movil.ratemypc.ui.screens.reviews.ReviewComponents.*
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun ReviewComponenteScreen(
    componenteId: String,
    onBack: () -> Unit,
    onWriteReview: () -> Unit,
    viewModel: ReviewComponenteViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(componenteId) {
        viewModel.loadData(componenteId)
    }

    uiState.componente?.let { comp ->
        ReviewComponenteContent(
            componente = comp,
            resenas = uiState.filteredResenas,
            selectedSource = uiState.selectedSource,
            onSourceChange = { viewModel.onSourceChange(it) },
            distribution = uiState.distribution,
            totalReviews = uiState.totalReviews,
            onBack = onBack,
            onWriteReview = onWriteReview
        )
    }
}

@Composable
fun ReviewComponenteContent(
    componente: ComponenteItem,
    resenas: List<ResenaItem>,
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
    MaterialTheme {
        RateMyPcTheme(darkTheme = true) {
            ReviewComponenteScreen(
                componenteId = "comp-3",
                onBack = {},
                onWriteReview = {},
                viewModel = ReviewComponenteViewModel()
            )
        }
    }
}
