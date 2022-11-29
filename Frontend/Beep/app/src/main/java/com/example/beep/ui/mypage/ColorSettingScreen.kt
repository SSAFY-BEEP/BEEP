package com.example.beep.ui.mypage

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorSettingScreen(
    navController: NavController,
    model: MyPageViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    painter = painterResource(R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                text = "테마색 설정"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {
            BeepImage(modifier = Modifier,"",model.themeNum)

            Spacer(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { model.themeNum = 1 },
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, BBI_YELLOW),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "화이트",
                            color = Color(android.graphics.Color.parseColor("#C2A600")),
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { model.themeNum = 2 },
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "블랙",
                            color = BLUE400,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { model.themeNum = 3 },
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(BLUE500),
                        border = BorderStroke(1.dp, BLUE500),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "블루",
                            color = PINK500,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            model.changeTheme()
                            MainApplication.sharedPreferencesUtil.saveTheme(model.themeNum)
                            Toast.makeText(context, "테마색이 변경되었습니다", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, BLUE500),
                    ) {
                        Text(
                            text = "등록",
                            color = BLUE500,
                            fontSize = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))

            }
        }
    }
}