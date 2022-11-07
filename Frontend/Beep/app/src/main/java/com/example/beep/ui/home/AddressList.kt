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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.ui.message.MessageViewModel

@Composable
fun ShowAddressList(viewModel: AddressViewModel) {
    var goAddAddress by remember { mutableStateOf(false) }

    var addressListTitle = if (goAddAddress) {
        "주소록 추가"
    } else {
        "주소록"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .width(320.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(0.dp, 0.dp, 0.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = addressListTitle,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                fontFamily = galmurinineFont
            )
            Button(
                onClick = { goAddAddress = !goAddAddress },
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .width(25.dp)
                    .height(25.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(0.3F)),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),

                ) {
                Text(
                    text = "+",
                    fontSize = 25.sp,
                    fontFamily = galmurinineFont
                )
            }
        }
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(240.dp)
                .padding(0.dp, 0.dp, 0.dp, 20.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(android.graphics.Color.parseColor("#7AA8FF")),
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            if(goAddAddress) {
                AddAddressSelf()
            } else{
                ViewAddressList(viewModel = viewModel)
            }


        }
    }


}