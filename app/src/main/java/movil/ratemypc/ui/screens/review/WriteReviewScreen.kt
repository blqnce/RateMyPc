package movil.ratemypc.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.ui.screens.review.ReviewComponents.*
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun WriteReviewScreen(
    componenteId: String? = null,
    onBack: () -> Unit = {},
    onSubmit: (String, Int, String, String?) -> Unit = { _, _, _, _ -> },
    viewModel: WriteReviewViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(componenteId) {
        componenteId?.let { viewModel.loadComponent(it) }
    }

    val finalComponent = uiState.component ?: ComponenteItem(
        id = "review-demo",
        nombre = "Tarjeta gráfica GeForce RTX 5060",
        imageUrl = "https://m.media-amazon.com/images/I/71ii5ow8slL._AC_SL1500_.jpg",
        costo = 1000f,
        consumoEnergetico = 450f,
        fechaLanzamiento = "2025/05/18",
        subCategoria = "GPU",
        marca = "GYGABITE",
        promedioCalificacion = 4f,
        totalResenas = 20
    )

    WriteReviewScreenContent(
        component = finalComponent,
        rating = uiState.rating,
        onRatingChange = { viewModel.onRatingChange(it) },
        reviewText = uiState.reviewText,
        onReviewTextChange = { viewModel.onReviewTextChange(it) },
        shops = uiState.shops,
        selectedShop = uiState.selectedShop,
        onShopChange = { viewModel.onShopChange(it) },
        imageUrl = uiState.imageUrl,
        onImageChange = { viewModel.onImageChange(it) },
        onBack = onBack,
        onSubmit = {
            onSubmit(uiState.reviewText, uiState.rating, uiState.selectedShop, uiState.imageUrl)
        },
        isValid = uiState.isValid
    )
}

@Composable
fun WriteReviewScreenContent(
    component: ComponenteItem,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    reviewText: String,
    onReviewTextChange: (String) -> Unit,
    shops: List<String>,
    selectedShop: String,
    onShopChange: (String) -> Unit,
    imageUrl: String?,
    onImageChange: (String?) -> Unit,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    isValid: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WriteReviewHeader(onBack = onBack)
        ProductReviewSummaryCard(component = component)
        RatingSelector(rating = rating, onRatingChange = onRatingChange)
        ReviewInputField(reviewText = reviewText, onReviewTextChange = onReviewTextChange)
        ShopSelector(
            shops = shops,
            selectedShop = selectedShop,
            onShopChange = onShopChange
        )
        ReviewImageUploader(
            imageUrl = imageUrl,
            onImageChange = onImageChange
        )
        ReviewSubmitButton(
            enabled = isValid,
            onClick = onSubmit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WriteReviewScreenPreview() {
    RateMyPcTheme {
        WriteReviewScreen(viewModel = WriteReviewViewModel())
    }
}
