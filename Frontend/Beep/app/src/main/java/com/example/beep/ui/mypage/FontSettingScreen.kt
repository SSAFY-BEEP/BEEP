package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.*

@Composable
fun FontSettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    model: MyPageViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
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
            modifier = modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp),
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            text = "폰트 설정"
        )

        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            TextButton(onClick = {
                model.fontNum = 1
                model.changeFont()
                MainApplication.sharedPreferencesUtil.saveFont(model.fontNum)

            }) {
                Text(text = "갈무리", fontFamily = galmurinineFont)
            }

            TextButton(onClick = {
                model.fontNum = 2
                model.changeFont()
                MainApplication.sharedPreferencesUtil.saveFont(model.fontNum)

            }) {
                Text(text = "둥근모", fontFamily = dunggeunmmoFont)
            }

            TextButton(onClick = {
                model.fontNum = 3
                model.changeFont()
                MainApplication.sharedPreferencesUtil.saveFont(model.fontNum)

            }) {
                Text(text = "랩디지털", fontFamily = labDigitalFont)
            }
            TextButton(onClick = {
                model.fontNum = 4
                model.changeFont()
                MainApplication.sharedPreferencesUtil.saveFont(model.fontNum)

            }) {
                Text(text = "라나픽셀", fontFamily = lanaPixelFont)
            }
        }
        BeepImage(modifier = Modifier,"폰트 테스트",model.themeNum,model.fontNum)
    }
}