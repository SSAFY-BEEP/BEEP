package com.example.beep.ui.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.login.LoginFormEvent
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EngraveScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    model: MyPageViewModel
) {
    val selectImage = MainApplication.sharedPreferencesUtil.getTheme()
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .clickable { keyboardController?.hide() },
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
                text = "각인 설정"
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BeepImage(Modifier, "", selectImage)

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = model.engraveText,
                onValueChange = { model.engraveText = it },
                singleLine = true,
                placeholder = {
                    Text(text = "각인을 적어주세요!")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(android.graphics.Color.parseColor("#7AA8FF")),
                    unfocusedBorderColor = Color(android.graphics.Color.parseColor("#9DBFFF"))
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        model.writeEngrave()
                        MainApplication.sharedPreferencesUtil.saveEngrave(model.engraveText)
                        keyboardController?.hide()
                    }
                ),

                )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
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

}
