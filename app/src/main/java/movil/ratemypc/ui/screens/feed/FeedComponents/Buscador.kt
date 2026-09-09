package movil.ratemypc.ui.screens.feed.FeedComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import movil.ratemypc.R

@Composable
fun Buscador(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
){
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(stringResource(R.string.buscar_componentes)) },
        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
        trailingIcon = {
            AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(Icons.Outlined.Close, contentDescription = stringResource(R.string.cancelar))
                }
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small
    )
}