package com.example.beep.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = galmurinineFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = GRAY500
    ),
    button = TextStyle(
        fontFamily = galmurinineFont,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = GRAY500
    ),


/* Other default text styles to override
   caption = TextStyle(
       fontFamily = FontFamily.Default,
       fontWeight = FontWeight.Normal,
       fontSize = 12.sp
   )
   */
)