package movil.ratemypc.ui.screens.compatibilidad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
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
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadComponents.ComponentChecklistRow
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadComponents.CompatibilityDetails
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadComponents.CompatibilityScoreCard
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadComponents.PowerBudgetCard
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


@Preview(showBackground = true)
@Composable
fun CompatibilidadScreenPreview() {
    RateMyPcTheme {
        CompatibilidadScreen()
    }
}