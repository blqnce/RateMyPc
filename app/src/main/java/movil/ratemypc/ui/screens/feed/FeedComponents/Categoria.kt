package movil.ratemypc.ui.screens.feed.FeedComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.local.LocalComponentesProvider

@Composable
fun Categoria(
){

    val componentes = remember { LocalComponentesProvider.componentes }

    var selectedCategory by remember { mutableStateOf("Todo") }


    val dynamicCategories = remember(componentes) {
        listOf("Todo") + componentes.map { it.subCategoria }.distinct().sorted()
    }


    Spacer(Modifier.height(16.dp))
}