package movil.ratemypc.ui.screens.compatibilidad

import movil.ratemypc.data.ComponenteItem

data class CompatibilidadState(
    val analyzed: Boolean = false,
    val selectedComponents: List<ComponenteItem> = emptyList(),
    val requiredCategories: List<String> = listOf("CPU", "GPU", "MOBO", "RAM", "PSU", "Storage", "Case", "Cooler"),
    val powerLimit: Int = 750
) {
    val selectedCategories: Set<String>
        get() = selectedComponents.map { it.subCategoria }.toSet()

    val score: Int
        get() = if (analyzed) (selectedCategories.size * 100 / requiredCategories.size).coerceAtMost(100) else 0

    val estimatedPower: Int
        get() = selectedComponents.sumOf { it.consumoEnergetico.toDouble() }.toInt()

    val totalCost: Double
        get() = selectedComponents.sumOf { it.costo.toDouble() }
}
