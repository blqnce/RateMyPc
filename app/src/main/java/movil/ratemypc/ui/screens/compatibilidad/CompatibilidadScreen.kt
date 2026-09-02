package movil.ratemypc.ui.screens.compatibilidad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import movil.ratemypc.data.ComponenteItem
import movil.ratemypc.ui.screens.compatibilidad.CompatibilidadComponents.*

@Composable
fun CompatibilidadScreen(
    compatibilidadViewModel: CompatibilidadViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val uiState by compatibilidadViewModel.uiState.collectAsState()

    CompatibilidadScreenContent(
        modifier = modifier,
        analyzed = uiState.analyzed,
        onAnalyzeClick = { compatibilidadViewModel.onAnalyzeClick() },
        score = uiState.score,
        estimatedPower = uiState.estimatedPower,
        powerLimit = uiState.powerLimit,
        totalCost = uiState.totalCost,
        selectedComponents = uiState.selectedComponents,
        selectedCategories = uiState.selectedCategories,
        requiredCategories = uiState.requiredCategories,
        onBack = onBack
    )
}

@Composable
fun CompatibilidadScreenContent(
    modifier: Modifier = Modifier,
    analyzed: Boolean,
    onAnalyzeClick: () -> Unit,
    score: Int,
    estimatedPower: Int,
    powerLimit: Int,
    totalCost: Double,
    selectedComponents: List<ComponenteItem>,
    selectedCategories: Set<String>,
    requiredCategories: List<String>,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
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
            CompatibilityScoreCard(score = score)
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
                onClick = onAnalyzeClick,
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
    movil.ratemypc.ui.theme.RateMyPcTheme {
        CompatibilidadScreen(onBack = {}, compatibilidadViewModel = CompatibilidadViewModel())
    }
}
