package com.example.beep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R


@ExperimentalComposeUiApi
@Composable
fun HomeScreen() {

    var sendText = false
    val image = painterResource(R.drawable.bbibbi_white)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#F5F8FF")))
            .wrapContentSize(Center)
    ) {
        Box {
            Image(
                painter = image,
                contentDescription = "삐삐 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .width(320.dp),
                contentScale = ContentScale.FillWidth
            )
            Button(
                onClick = { sendText = !sendText },
                modifier = Modifier
                    .border(2.dp, color = Color.Red)
                    .width(50.dp)
                    .height(50.dp)
                    .padding(300.dp, 200.dp)
                    .background(color = Color.Black)
            ) {

            }
            if (sendText) {
                askGoingToRecord()
            } else {
                viewMyText()
            }
        }
        Text(
            text = "주소록",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 20.dp, bottom = 20.dp),
            fontSize = 16.sp,
        )
        getKeyboard()
    }
}




@Composable
fun viewMyText() {
    val viewModel = viewModel<KeyboardViewModel>()
    val state = viewModel.state
    Text(
        text = state.number1,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 45.dp),
        fontSize = 20.sp,
    )
}

@Composable
fun askGoingToRecord() {
    Text(
        text = "음성녹음을 하시겠습니까?",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 45.dp),
        fontSize = 20.sp,
    )
}

