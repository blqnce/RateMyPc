package movil.ratemypc.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MainBlue,
    onPrimary = TextWhite,
    background = BackgroundGrey,
    surface = BackgroundGrey,
    surfaceVariant = Color(0xFF4A4C4B),
    onBackground = TextWhite,
    onSurface = TextWhite,
    onSurfaceVariant = TextGrey
)

private val LightColorScheme = lightColorScheme(
    primary = MainBlue,
    onPrimary = TextWhite,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color(0xFFF0F0F0),
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Gray
)

@Composable
fun RateMyPcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled by default to show your custom colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
