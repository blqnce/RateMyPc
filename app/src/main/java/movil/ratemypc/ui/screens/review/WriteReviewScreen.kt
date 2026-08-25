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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.screens.review.ReviewComponents.ProductReviewSummaryCard
import movil.ratemypc.ui.screens.review.ReviewComponents.RatingSelector
import movil.ratemypc.ui.screens.review.ReviewComponents.ReviewImageUploader
import movil.ratemypc.ui.screens.review.ReviewComponents.ReviewInputField
import movil.ratemypc.ui.screens.review.ReviewComponents.ReviewSubmitButton
import movil.ratemypc.ui.screens.review.ReviewComponents.ShopSelector
import movil.ratemypc.ui.screens.review.ReviewComponents.WriteReviewHeader
import movil.ratemypc.ui.theme.RateMyPcTheme

@Composable
fun WriteReviewScreen(
    component: Componente = Componente(
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
    ),
    onBack: () -> Unit = {},
    onSubmit: (String, Int, String, String?) -> Unit = { _, _, _, _ -> }
) {
    var rating by remember { mutableIntStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    var selectedShop by remember { mutableStateOf("Amazon") }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    val shops = listOf("Amazon", "Newegg", "Micro Center", "B&H")
    val isValid = rating > 0 && reviewText.isNotBlank() && selectedShop.isNotBlank()

    WriteReviewScreenContent(
        component = component,
        rating = rating,
        onRatingChange = { rating = it },
        reviewText = reviewText,
        onReviewTextChange = { reviewText = it },
        shops = shops,
        selectedShop = selectedShop,
        onShopChange = { selectedShop = it },
        imageUrl = imageUrl,
        onImageChange = { imageUrl = it },
        onBack = onBack,
        onSubmit = {
            onSubmit(reviewText, rating, selectedShop, imageUrl)
        },
        isValid = isValid
    )
}

@Composable
fun WriteReviewScreenContent(
    component: Componente,
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
        WriteReviewScreen()
    }
}
