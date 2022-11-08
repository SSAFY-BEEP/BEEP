package com.example.beep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R
import com.example.beep.util.collectAsStateLifecycleAware

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@ExperimentalComposeUiApi
@Composable
fun HomeScreen() {
//    val addressList = viewModel.exampleEntities.collectAsStateLifecycleAware(initial = listOf())
    var sendText by remember { mutableStateOf(false) }
    val image = painterResource(R.drawable.bbibbi_white)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#F5F8FF")))
            .wrapContentSize(Center),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                onClick = { /*showMessage*/ },
                modifier = Modifier
                    .width(70.dp)
                    .offset(60.dp, 133.dp)
                    .height(45.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(0.1F)),
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 30.dp)
            ) {

            }
            Button(
                onClick = { sendText = !sendText },
                modifier = Modifier
                    .width(83.dp)
                    .offset(252.dp, 110.dp)
                    .height(67.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(0.0001F)),
                shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
                ) {

            }
            if (sendText) {
                AskGoingToRecord()
            } else {
                ViewMyText()
            }
        }
        KeyboardVsAddressChoice()
    }
}






@Preview
@Composable
fun ViewMyText() {
    val viewModel = viewModel<KeyboardViewModel>()
    val state = viewModel.state
    Text(
        text = state.number1,
        modifier = Modifier

            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 45.dp),
        fontSize = 19.sp,
        fontFamily = galmurinineFont
    )
}


