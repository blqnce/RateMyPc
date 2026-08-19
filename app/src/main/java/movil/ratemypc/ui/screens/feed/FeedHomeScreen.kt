package movil.ratemypc.ui.screens.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.screens.auth.AuthComponents.RegisterComponents.RegisterHeader
import movil.ratemypc.ui.screens.feed.FeedComponents.Buscador
import movil.ratemypc.ui.screens.feed.FeedComponents.FeedHeader
import movil.ratemypc.ui.screens.feed.FeedComponents.FeedItemCard
import movil.ratemypc.ui.screens.feed.FeedComponents.MsgEncontrados
import movil.ratemypc.ui.screens.feed.FeedComponents.NotFound
import movil.ratemypc.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedHomeScreen() {

    FeedhomeScreenContent()
}

@Composable
fun FeedhomeScreenContent(
    viewModel: ComponenteViewModel = viewModel()

){

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(Modifier.height(16.dp))

                FeedHeader()

                Spacer(Modifier.height(16.dp))

                Buscador(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )

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
            MsgEncontrados(count = filtered.size)
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

@Preview
@Composable
fun FeedHomeScreenComposable(){
    FeedHomeScreen()
}

@Preview(showBackground = true)
@Composable
fun BuscadorPreview(){
    Buscador(
        searchQuery = "",
        onSearchQueryChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FeedheaderPreview(){
    FeedHeader()
}

@Preview(showBackground = true)
@Composable
fun MsgEncontradosPreview(){
    MsgEncontrados(
        count = 0
    )
}

@Preview(showBackground = true)
@Composable
fun NotFoundPreview(){
    NotFound()
}



