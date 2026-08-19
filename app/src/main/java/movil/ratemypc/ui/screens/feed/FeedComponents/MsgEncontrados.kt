package movil.ratemypc.ui.screens.feed.FeedComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import movil.ratemypc.R

@Composable
fun MsgEncontrados(
    count: Int
){
    Text(
        text = stringResource(R.string.componentes_encontrados, count),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(Modifier.height(12.dp))
}