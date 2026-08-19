package movil.ratemypc.ui.screens.compatibilidad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import movil.ratemypc.data.Componente
import movil.ratemypc.ui.theme.RateMyPcTheme
import movil.ratemypc.viewmodel.ComponenteViewModel

@Composable
fun CompatibilidadScreen(
    viewModel: ComponenteViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val componentes by viewModel.componentes.collectAsState()
    var analyzed by remember { mutableStateOf(false) }
    val selectedComponents = componentes.take(2)
    val selectedCategories = selectedComponents.map { it.subCategoria }.toSet()
    val requiredCategories = listOf("CPU", "GPU", "MOBO", "RAM", "PSU", "Storage", "Case", "Cooler")
    val score = (selectedCategories.size * 100 / requiredCategories.size).coerceAtMost(100)
    val estimatedPower = selectedComponents.sumOf { it.consumoEnergetico.toDouble() }.toInt()
    val powerLimit = 750
    val totalCost = selectedComponents.sumOf { it.costo.toDouble() }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Compatibilidad",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Revisa si los componentes seleccionados pueden formar una build.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            CompatibilityScoreCard(score = if (analyzed) score else 0)
        }

        item {
            PowerBudgetCard(estimatedPower = estimatedPower, powerLimit = powerLimit)
        }

        item {
            Text(
                text = "Componentes de la build",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(requiredCategories) { category ->
            val included = category in selectedCategories
            ComponentChecklistRow(category = category, included = included)
        }

        item {
            val missing = requiredCategories.count { it !in selectedCategories }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (missing == 0) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (missing == 0) Icons.Filled.CheckCircle else Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = if (missing == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.size(12.dp))
                    Text(
                        text = if (missing == 0) "Todos los componentes están completos."
                        else "Faltan $missing categorías para una build completa.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        item {
            CompatibilityDetails(
                missingCategories = requiredCategories.filter { it !in selectedCategories },
                selectedComponents = selectedComponents
            )
        }

        item {
            Button(
                onClick = { analyzed = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (analyzed) "Actualizar análisis" else "Analizar compatibilidad")
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Costo estimado de la build",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "$${"%.2f".format(totalCost)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "${selectedComponents.size} piezas",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun CompatibilityScoreCard(
    score: Int,
    modifier: Modifier = Modifier
) {
    val scoreColor = when {
        score >= 75 -> MaterialTheme.colorScheme.primary
        score >= 40 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Puntuación de compatibilidad", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { score / 100f },
                    modifier = Modifier.size(132.dp),
                    color = scoreColor,
                    trackColor = MaterialTheme.colorScheme.surface
                )
                Text(
                    text = "$score%",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (score == 0) "Pulsa analizar para comprobar la build" else "Análisis basado en los componentes seleccionados",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CompatibilityDetails(
    missingCategories: List<String>,
    selectedComponents: List<Componente>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Problemas detectados", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (missingCategories.isEmpty()) {
                        "No se detectaron categorías faltantes."
                    } else {
                        "Faltan: ${missingCategories.joinToString()}."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Puntos destacados", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (selectedComponents.isEmpty()) {
                        "Agrega componentes para iniciar el análisis."
                    } else {
                        "${selectedComponents.size} componentes listos para revisar."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun PowerBudgetCard(
    estimatedPower: Int,
    powerLimit: Int,
    modifier: Modifier = Modifier
) {
    val progress = (estimatedPower.toFloat() / powerLimit).coerceIn(0f, 1f)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Presupuesto de energía", style = MaterialTheme.typography.titleMedium)
                Text("$estimatedPower / $powerLimit W", style = MaterialTheme.typography.labelMedium)
            }
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ComponentChecklistRow(
    category: String,
    included: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (included) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (included) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.size(12.dp))
        Text(category, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.weight(1f))
        Text(
            text = if (included) "Incluido" else "Pendiente",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompatibilidadScreenPreview() {
    RateMyPcTheme {
        CompatibilidadScreen(viewModel = ComponenteViewModel())
    }
}