package movil.ratemypc.ui.screens.perfil.PerfilComponents

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import movil.ratemypc.R

@Composable
fun BoxScope.EditarPerfil(){
    OutlinedButton(
        onClick = { /* Edit */ },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 8.dp, end = 16.dp),
        shape = MaterialTheme.shapes.extraLarge,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(stringResource(R.string.editar_perfil), fontWeight = FontWeight.Bold)
    }
}