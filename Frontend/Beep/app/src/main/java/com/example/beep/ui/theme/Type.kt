package com.example.beep.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val TypographyGal = Typography(
    galmurinineFont,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = GRAY500
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = GRAY500
    ),
)

val TypographyDung = Typography(
    dunggeunmmoFont,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = GRAY500
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = GRAY500
    ),
)

val TypographyLab = Typography(
    labDigitalFont,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = GRAY500
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = GRAY500
    ),
)

val TypographyLana = Typography(
    lanaPixelFont,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = GRAY500
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = GRAY500
    ),
)

/* Other default text styles to override
   caption = TextStyle(
       fontFamily = FontFamily.Default,
       fontWeight = FontWeight.Normal,
       fontSize = 12.sp
   )
   */