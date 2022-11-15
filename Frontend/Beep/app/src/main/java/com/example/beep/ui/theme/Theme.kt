@file:Suppress("SpellCheckingInspection")

package com.example.beep.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.beep.R


val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

val dunggeunmmoFont = FontFamily(
    Font(R.font.dunggeunmo)
)

val labDigitalFont = FontFamily(
    Font(R.font.labdigital)
)

val lanaPixelFont = FontFamily(
    Font(R.font.lanapixel)
)


private val DarkColorPalette = darkColors(
    primary = PINK500,
    primaryVariant = BLUE500,
    secondary = GRAY500
)

private val LightColorPalette = lightColors(
    primary = BLUE500,
    primaryVariant = PINK500,
    secondary = GRAY500

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BeepTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = TypographyGal,
        shapes = Shapes,
        content = content
    )
}

