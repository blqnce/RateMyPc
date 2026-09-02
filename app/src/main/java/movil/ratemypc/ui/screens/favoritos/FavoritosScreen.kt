package movil.ratemypc.ui.screens.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.*

@Composable
fun FavoritosScreen(
    favoritosViewModel: FavoritosViewModel,
    onOpenCompatibility: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by favoritosViewModel.uiState.collectAsState()

    FavoritosScreenContent(
        modifier = modifier,
        favoritos = uiState.favoritos,
        selectedTab = uiState.selectedTab,
        onTabSelected = { favoritosViewModel.onTabSelected(it) },
        onOpenCompatibility = onOpenCompatibility
    )
}

@Composable
fun FavoritosScreenContent(
    modifier: Modifier = Modifier,
    favoritos: List<ComponenteItem>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onOpenCompatibility: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FavoritosHeader(favoritosCount = favoritos.size)
        }

        item {
            FavoritosTabSelector(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }

        if (selectedTab == 0) {
            items(favoritos, key = { it.id }) { componente ->
                FavoriteProductCard(componente = componente)
            }
        } else {
            item {
                EmptyFavoriteTab(
                    title = if (selectedTab == 1) "No hay builds guardadas" else "No hay reseñas guardadas",
                    description = "Guarda contenido desde el feed para verlo aquí."
                )
            }
        }

        item {
            CompatibilityButton(onClick = onOpenCompatibility)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FavoritosScreenPreview() {
    movil.ratemypc.ui.theme.RateMyPcTheme {
        FavoritosScreen(favoritosViewModel = FavoritosViewModel())
    }
}
