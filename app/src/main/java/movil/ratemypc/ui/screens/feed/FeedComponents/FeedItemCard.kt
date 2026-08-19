package movil.ratemypc.ui.screens.feed.FeedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import movil.ratemypc.data.Componente

@Composable
fun FeedItemCard(
    item: Componente,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Modo oscuro de las cards
    val isDark = isSystemInDarkTheme()
    val containerColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val textColor = if (isDark) Color.Black else MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = if (isDark) Color(0xFF444444) else MaterialTheme.colorScheme.onSurfaceVariant
    val buttonBg = if (isDark) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant
    val buttonIconTint = if (isDark) Color.White else textColor

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Outlined.ImageNotSupported,
                            contentDescription = "Error al cargar",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    contentDescription = item.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.marca.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = secondaryTextColor
                    )

                    val badgeColor = when (item.subCategoria.uppercase()) {
                        "GPU" -> Color(0xFF9C27B0) // Púrpura
                        "CPU" -> Color(0xFFE65100) // Naranja
                        else -> MaterialTheme.colorScheme.secondary
                    }
                    Surface(
                        shape = CircleShape,
                        color = badgeColor,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = item.subCategoria.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = androidx.compose.ui.unit.TextUnit.Unspecified // Mantener tamaño por defecto o ajustar
                            ),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Lanzamiento: ${item.fechaLanzamiento}",
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryTextColor
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "$${item.costo}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textColor
                        )


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                val isFilled = index < item.promedioCalificacion.toInt()
                                Icon(
                                    imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = if (isFilled) (if (isDark) Color(0xFF00BCD4) else MaterialTheme.colorScheme.primary) else MaterialTheme.colorScheme.outline
                                )
                            }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "${item.promedioCalificacion} (${item.totalResenas})",
                                style = MaterialTheme.typography.labelSmall,
                                color = secondaryTextColor
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                            IconButton(
                                onClick = {
                                    // Navegar a la pantalla de informacion
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(buttonBg, CircleShape)
                            ) {
                                Icon(
                                    Icons.Outlined.Info,
                                    contentDescription = "Info",
                                    modifier = Modifier.size(18.dp),
                                    tint = buttonIconTint
                                )
                            }
                            IconButton(
                                onClick = {
                                    // Añadir el componente a la build que se esta creando
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(buttonBg, CircleShape)
                            ) {
                                Icon(
                                    Icons.Outlined.Add,
                                    contentDescription = "Agregar",
                                    modifier = Modifier.size(18.dp),
                                    tint = buttonIconTint
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
