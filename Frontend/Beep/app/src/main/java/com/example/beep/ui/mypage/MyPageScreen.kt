package com.example.beep.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.BACKGROUND_WHITE
import com.example.beep.ui.theme.GRAY100

@Composable
fun MyPageScreen(onClickMenu: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
    ) {
        val scrollState = rememberScrollState()

        val phone_preset = painterResource(R.drawable.setting_number)
        val message_preset = painterResource(R.drawable.setting_message)
        val introduce_record = painterResource(R.drawable.setting_record)
        val font = painterResource(R.drawable.setting_font)
        val ring = painterResource(R.drawable.setting_ring)
        val password = painterResource(R.drawable.setting_password)
        val theme = painterResource(R.drawable.setting_theme)
        val engrave = painterResource(R.drawable.setting_engrave)
        val signout = painterResource(R.drawable.setting_signout)
        val logout = painterResource(R.drawable.setting_logout)
        val ban = painterResource(R.drawable.setting_ban)


        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(69.dp)
                    .drawBehind {
                        val borderSize = 2.dp.toPx()
                        val y = size.height - borderSize / 2
                        drawLine(
                            color = GRAY100,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = borderSize
                        )
                    }
            ) {
                Text(
                    text = "??????",
                    modifier = Modifier
                        .padding(30.dp, 12.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )

                ) {
                    CustomTextTitle("????????? ??????")
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        CustomText1({ onClickMenu("contactPreset") }, "????????? ????????? ??????", phone_preset)
                        CustomText1({ onClickMenu("messagePreset") }, "????????? ????????? ??????", message_preset)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    CustomTextTitle("????????? ??????")
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomText1({ onClickMenu("greetingPreset") }, "????????? ??????", introduce_record)
                    }
                }


                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    CustomTextTitle("?????? ??????")
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        CustomText1({ onClickMenu("colorSetting") }, "????????? ??????" ,theme)
                        CustomText1({ onClickMenu("engravingSetting") }, "?????? ??????", engrave)
                        // ?????? ????????? ?????? ????????? ????????? ????????? ????????? ?????? ??????
                        // CustomText1({ onClickMenu("fontSetting") }, "?????? ??????", font)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    CustomTextTitle("?????? ??????")
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomText1({ onClickMenu("passwordChange") }, "???????????? ??????", password)

                        Button(
                            onClick = {MainApplication.sharedPreferencesUtil.deleteToken()},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(43.dp)
                                .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            elevation = null
                        ) {
                            Image(
                                painter = logout,
                                contentDescription = "?????????",
                                modifier = Modifier.width(32.dp),
                                contentScale = ContentScale.FillWidth
                            )
                            Text(
                                "????????????",
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(10.dp, 0.dp)
                                    .fillMaxWidth()
                            )
                        }

                        CustomText1({ onClickMenu("") }, "?????? ??????", signout)
                        CustomText1({ onClickMenu("blockScreen") }, "?????? ??????", ban)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomText1(onClickMenu: () -> Unit, text: String, painter: Painter) {
    Button(
        onClick = onClickMenu,
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp)
            .padding(10.dp, 0.dp, 0.dp, 0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        elevation = null
    ) {
        Image(
            painter = painter,
            contentDescription = "?????????",
            modifier = Modifier.width(20.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CustomTextTitle(text: String) {
    Text(
        text,
        fontSize = 17.sp,
        modifier = Modifier
            .padding(20.dp, 13.dp)
            .fillMaxWidth(),
        fontWeight = FontWeight.Bold
    )
}