package movil.ratemypc.ui.screens.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.theme.RateMyPcTheme
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun FavoritosScreen(
    viewModel: ComponenteViewModel = viewModel(),
    onOpenCompatibility: () -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Mis favoritos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${favoritos.size} guardados",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                listOf("Productos", "Builds", "Reseñas").forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 3
                        ),
                        label = { Text(label) }
                    )
                }
            }
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
            Button(
                onClick = onOpenCompatibility,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Outlined.Build, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Comprobar compatibilidad")
            }
        }
    }
}

@Composable
private fun FavoriteProductCard(
    componente: Componente,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = componente.marca.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = componente.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${componente.subCategoria} · $${componente.costo}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.size(4.dp))
                    Text("${componente.promedioCalificacion}")
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoriteTab(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritosScreenPreview() {
    RateMyPcTheme {
        FavoritosScreen(
            viewModel = ComponenteViewModel(),
            onOpenCompatibility = {}
        )
    }
}