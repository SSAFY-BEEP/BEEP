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

@Composable
fun ShowAddressList(
) {
    var goAddAddress by remember { mutableStateOf(false) }
    var viewEditDelBtn by remember { mutableStateOf(false) }
    var goPatchAddress by remember { mutableStateOf(false) }
    var defaultNameString by remember { mutableStateOf("") }
    var defaultPhoneString by remember { mutableStateOf("") }

    var themeColorBlue = Color(android.graphics.Color.parseColor("#7AA8FF"))


    var addressListTitle = if (goAddAddress) {
        "주소록 추가"
    } else if(goPatchAddress) {
    "주소록 수정"
    } else {
        "주소록"
    }



    var goEditDelBtnTxt = if (viewEditDelBtn) {
        "SUBMIT"
    } else {
        "EDIT"
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
                fontFamily = galmurinineFont,
                color = themeColorBlue
            )
            if (!goAddAddress) {
                Button(
                    onClick = { viewEditDelBtn = !viewEditDelBtn },
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        .width(60.dp)
                        .height(25.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = themeColorBlue),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = goEditDelBtnTxt,
                        fontSize = 14.sp,
                        fontFamily = galmurinineFont,
                        color = Color.White
                    )
                }
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
                    color = themeColorBlue,
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            if(goAddAddress) {
                AddressPostSelf(
                    changeToAddAddress = { goAddAddress = !goAddAddress },
                    )
            } else if(goPatchAddress) {
                AddressPatch (
                    changeToPatchAddress = { goPatchAddress = !goPatchAddress },
                    defaultNameString = defaultNameString,
                    defaultPhoneString = defaultPhoneString,
                    )
            } else{
                AddressListContent(
                    viewEditDelBtn = viewEditDelBtn,
                    changeToAddAddress = { goAddAddress = !goAddAddress },
                    changeToPatchAddress = { goPatchAddress = !goPatchAddress },
                    changeDefaultNameString = { defaultName: String -> defaultNameString = defaultName },
                    changeDefaultPhoneString = { defaultPhone: String -> defaultPhoneString = defaultPhone }
                )
            }


        }
    }


}