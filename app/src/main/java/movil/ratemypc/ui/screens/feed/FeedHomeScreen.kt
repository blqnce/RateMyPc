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
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.ui.screens.feed.FeedComponents.*

@Composable
fun FeedHomeScreen(
    feedHomeViewModel: FeedHomeViewModel,
    onOpenReview: (String) -> Unit = {},
    onOpenDetail: (String) -> Unit = {}
) {
    val uiState by feedHomeViewModel.uiState.collectAsState()

    FeedhomeScreenContent(
        selectedCategory = uiState.selectedCategory,
        onCategoryChange = { feedHomeViewModel.onCategoryChange(it) },
        searchQuery = uiState.searchQuery,
        onSearchQueryChange = { feedHomeViewModel.onSearchQueryChange(it) },
        dynamicCategories = uiState.dynamicCategories,
        filtered = uiState.filteredComponentes,
        onOpenReview = onOpenReview,
        onOpenDetail = onOpenDetail,
        onToggleFavorite = { feedHomeViewModel.toggleFavorite(it) }
    )
}

@Composable
fun FeedhomeScreenContent(
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    dynamicCategories: List<String>,
    filtered: List<ComponenteItem>,
    onOpenReview: (String) -> Unit = {},
    onOpenDetail: (String) -> Unit = {},
    onToggleFavorite: (String) -> Unit = {}
){
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
                    onSearchQueryChange = onSearchQueryChange
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
                        onClick = { onCategoryChange(subcategoria) },
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
                    onOpenReview(item.id)
                },
                onInfoClick = {
                    onOpenDetail(item.id)
                },
                onFavoriteClick = {
                    onToggleFavorite(item.id)
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
    FeedHomeScreen(feedHomeViewModel = FeedHomeViewModel())
}
