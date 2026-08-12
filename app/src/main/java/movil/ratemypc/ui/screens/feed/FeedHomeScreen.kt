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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import movil.ratemypc.R
import movil.ratemypc.data.Componente
import movil.ratemypc.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedHomeScreen(
    viewModel: ComponenteViewModel = viewModel()
) {
    // Variables usadas para el filtro

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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "logo",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "RateMyPC",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar componentes") },
                        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                        trailingIcon = {
                            AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Outlined.Close, contentDescription = "Limpiar")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
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
                Text(
                    text = "${filtered.size} componentes encontrados",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(12.dp))
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "No se encontraron componentes",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Intenta con otros filtros o busca algo diferente",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// Card individual de componente

@Composable
private fun FeedItemCard(
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




