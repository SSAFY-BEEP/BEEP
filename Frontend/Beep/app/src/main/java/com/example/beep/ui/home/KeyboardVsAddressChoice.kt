package com.example.beep.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalComposeUiApi
@Composable
fun KeyboardVsAddressChoice() {

    var showKeyboard by remember { mutableStateOf(true) }


    var keyboardBtnBgColor = if (showKeyboard) {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    } else {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    }
    var keyboardBtnTxtColor = if (showKeyboard) {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    } else {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    }
    var addressBtnBgColor = if (showKeyboard) {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    } else {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    }
    var addressBtnTxtColor = if (showKeyboard) {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    } else {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    }

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .width(200.dp)
            .height(30.dp)
            .background(color = androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(15.dp))
            .border(width = 1.dp, color = Color(android.graphics.Color.parseColor("#7AA8FF")), shape = RoundedCornerShape(15.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .wrapContentWidth(Alignment.CenterHorizontally)

        ) {
            Button(
                onClick = { showKeyboard = true },
                modifier = Modifier
                    .width(97.dp)
                    .height(24.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = keyboardBtnBgColor),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),

            ) {
                Text(
                    text = "키보드",
                    Modifier.padding(0.dp),
                    color = keyboardBtnTxtColor

                )
            }
            Button(
                onClick = { showKeyboard = false },
                modifier = Modifier
                    .width(97.dp)
                    .height(24.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = addressBtnBgColor),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "주소록",
                    modifier = Modifier
                        .padding(top = 0.dp),
                    color = addressBtnTxtColor
                )
            }

        }
    }
    if (showKeyboard) {
        getKeyboard()

    } else {
        ShowAddressList()
    }

}