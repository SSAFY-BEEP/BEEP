package com.example.beep.data

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Painter,
    val selectIcon: Painter
    )
