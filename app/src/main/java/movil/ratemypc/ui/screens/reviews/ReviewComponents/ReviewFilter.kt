package movil.ratemypc.ui.screens.reviews.ReviewComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ReviewFilter(
    selectedSource: String,
    onSourceChange: (String) -> Unit
) {
    val sources = listOf("All", "Newegg", "Amazon", "Micro Center")
    
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sources) { source ->
            val isSelected = selectedSource == source
            FilterChip(
                selected = isSelected,
                onClick = { onSourceChange(source) },
                label = { Text(source) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
