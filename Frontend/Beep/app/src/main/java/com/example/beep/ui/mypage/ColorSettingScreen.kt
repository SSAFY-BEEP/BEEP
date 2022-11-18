package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.BeepImage

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

        BeepImage(modifier = Modifier,"",model.themeNum)

        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    model.themeNum = 1
                }) {
                    Text(text = "하양")
                }
                Button(onClick = {
                    model.themeNum = 2
                }) {
                    Text(text = "블랙핑크")
                }
                Button(onClick = {
                    model.themeNum = 3
                }) {
                    Text(text = "파랑")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "뒤로가기")
                }
                Button(onClick = {
                    model.changeTheme()
                    MainApplication.sharedPreferencesUtil.saveTheme(model.themeNum)
                }) {
                    Text(text = "설정")
                }
            }

        }
    }
}