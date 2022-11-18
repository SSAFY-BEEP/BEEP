package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.ui.theme.BeepImage

//@Preview
@Composable
fun EngravingSettingScreen(navController: NavController, modifier: Modifier = Modifier, model: MyPageViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentUiState = model.userDataScreenState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {
                EngraveScreen(
                    navController,
                    modifier = Modifier,
                    model = model)
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun EngraveScreen(navController: NavController, modifier: Modifier = Modifier, model: MyPageViewModel) {
    val selectImage = MainApplication.sharedPreferencesUtil.getTheme()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            text = "각인 설정"
        )

        BeepImage(Modifier,"",selectImage)
        TextField(value = model.engraveText, onValueChange = { model.engraveText = it })
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "취소")
            }
            Button(onClick = {
                model.writeEngrave()
                MainApplication.sharedPreferencesUtil.saveEngrave(model.engraveText)
            }) {
                Text(text = "수정")
            }
        }
        Spacer(modifier = modifier.height(100.dp))
    }
}
