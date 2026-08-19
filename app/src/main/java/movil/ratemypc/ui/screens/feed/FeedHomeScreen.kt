package movil.ratemypc.ui.screens.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import movil.ratemypc.R
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.screens.auth.LoginScreen
import movil.ratemypc.ui.screens.feed.FeedComponents.Buscador
import movil.ratemypc.ui.screens.feed.FeedComponents.Categoria
import movil.ratemypc.ui.screens.feed.FeedComponents.FeedHeader
import movil.ratemypc.ui.screens.feed.FeedComponents.FeedItemCard
import movil.ratemypc.ui.screens.feed.FeedComponents.MsgEncontrados
import movil.ratemypc.ui.screens.feed.FeedComponents.NotFound
import movil.ratemypc.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedHomeScreen(
    viewModel: ComponenteViewModel = viewModel()
) {

    var selectedCategory by remember { mutableStateOf("Todo") }
    var searchQuery by remember { mutableStateOf("") }

    val componentes by viewModel.componentes.collectAsState()

    val dynamicCategories = remember(componentes) {
        listOf("Todo") + componentes.map { it.subCategoria }.distinct().sorted()
    }

    val filtered = componentes.filter { componente ->
        val matchesCategory = selectedCategory == "Todo" || componente.subCategoria == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                componente.nombre.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(16.dp))

                    FeedHeader()

                    Spacer(Modifier.height(16.dp))

                    Buscador()

                    Spacer(Modifier.height(12.dp))
                }
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dynamicCategories) { subcategoria ->
                        val isSelected = selectedCategory == subcategoria
                        AssistChip(
                            onClick = { selectedCategory = subcategoria },
                            label = { Text(subcategoria) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceVariant,
                                labelColor = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = if (isSelected)
                                BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            else
                                AssistChipDefaults.assistChipBorder(true)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                MsgEncontrados()
            }

            items(filtered) { item ->
                FeedItemCard(
                    item = item,
                    onClick = {
                        // Navegar a la pantalla de detalle del componente
                    }
                )
            }

            if (filtered.isEmpty()) {
                item {
                    NotFound()
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedHomeScreenComposable(){
    FeedHomeScreen()
}




