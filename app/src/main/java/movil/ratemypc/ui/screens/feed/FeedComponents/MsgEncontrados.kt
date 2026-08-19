package movil.ratemypc.ui.screens.feed.FeedComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.R
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun MsgEncontrados(
    viewModel: ComponenteViewModel = viewModel()
){

    var selectedCategory by remember { mutableStateOf("Todo") }
    var searchQuery by remember { mutableStateOf("") }

    val componentes by viewModel.componentes.collectAsState()

    val filtered = componentes.filter { componente ->
        val matchesCategory = selectedCategory == "Todo" || componente.subCategoria == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                componente.nombre.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Text(
        text = stringResource(R.string.componentes_encontrados, filtered.size),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(Modifier.height(12.dp))



}