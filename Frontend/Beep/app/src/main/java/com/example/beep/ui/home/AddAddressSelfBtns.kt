package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview
@Composable
fun AddCancelBtn() {
    Button(
        onClick = { /*showMessage*/ },
        modifier = Modifier
            .height(40.dp)
            .border(
                width = 1.dp,
                color = Color(android.graphics.Color.parseColor("#7AA8FF")),
                shape = RoundedCornerShape(10.dp)
            ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),
        ) {
        Text(
            text = "취소",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color(android.graphics.Color.parseColor("#7AA8FF"))
        )
    }
}

@Composable
fun AddToBookBtn() {
    Button(
        onClick = { /*showMessage*/ },
        modifier = Modifier
            .height(40.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#7AA8FF"))),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),

        ) {
        Text(
            text = "주소록",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun AddSubmitBtn(
    name: String,
    phone: String,
    viewModel: AddAddressSelfViewModel = viewModel()
) {
    Button(
        onClick = {
            Log.d("PHONE", phone)
            Log.d("NAME", name)
            viewModel.postAddress(phone, name) },
        modifier = Modifier
            .height(40.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#7AA8FF"))),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),
        ) {
        Text(
            text = "등록",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}