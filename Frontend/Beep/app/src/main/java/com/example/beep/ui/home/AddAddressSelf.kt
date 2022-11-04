package com.example.beep.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddAddressSelf() {
    Column(
        modifier = Modifier
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
    ) {
        Row() {
            Text(text = "이름")
        }
        Row() {
            Text(text = "연락처")
        }
        Row() {
            Text(text = "ssssssssssss")
        }

    }
}
