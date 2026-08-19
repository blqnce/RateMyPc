package movil.ratemypc.ui.screens.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.CompatibilityButton
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.EmptyFavoriteTab
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.FavoriteProductCard
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.FavoritosHeader
import movil.ratemypc.ui.screens.favoritos.FavoritosComponents.FavoritosTabSelector
import movil.ratemypc.ui.theme.RateMyPcTheme
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun FavoritosScreen(
    viewModel: ComponenteViewModel = viewModel(),
    onOpenCompatibility: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val componentes by viewModel.componentes.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val favoritos = componentes.take(2)

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
                onTabSelected = { selectedTab = it }
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
    RateMyPcTheme {
        FavoritosScreen()
    }
}