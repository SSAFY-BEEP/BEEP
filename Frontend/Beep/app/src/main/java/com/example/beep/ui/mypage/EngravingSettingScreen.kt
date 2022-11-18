package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
fun EngravingSettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    model: MyPageViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (val currentUiState = model.userDataScreenState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {
                EngraveScreen(
                    navController,
                    modifier = Modifier,
                    model = model
                )
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun EngraveScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    model: MyPageViewModel
) {
    val selectImage = MainApplication.sharedPreferencesUtil.getTheme()

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
            text = "각인 설정"
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        BeepImage(Modifier, "", selectImage, engrave = model.engraveText)
        TextField(
            value = model.engraveText,
            onValueChange = { model.engraveText = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )

        )
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
