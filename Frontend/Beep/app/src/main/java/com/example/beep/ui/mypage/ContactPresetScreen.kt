package com.example.beep.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.PresetViewModel
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.ui.theme.BLUE500
import com.example.beep.ui.theme.PINK500

@Composable
fun ContactPresetScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel,
    presetViewModel: PresetViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit) {
        presetViewModel.getPresetByToken(2)
    }

    when (val currentUiState = presetViewModel.contactPreset) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            ContactPresetSuccessScreen(
                navController,
                modifier,
                presetList = currentUiState.data,
                presetViewModel
            )
        }
        is UiState.Error -> {
            ErrorScreen()
        }
    }
}


@Composable
fun ContactPresetSuccessScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    presetList: Array<String?>,
    viewModel: PresetViewModel
) {
    val scrollState = rememberScrollState()
    val openDialog = remember { mutableStateOf(false) }
    var clickNum = remember { mutableStateOf(0) }     //클릭된 수
    var content = remember { mutableStateOf("${presetList[clickNum.value] ?: ""}") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier
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
                    modifier = modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    text = "연락처 단축키 설정"
                )
            }


//        //수정, 입력 창
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(
                            text = "단축키 ${clickNum.value}번 설정",
                            fontWeight = FontWeight.Bold,
                            modifier = modifier
                                .padding(bottom = 30.dp)
                                .height(30.dp)
                        )
                    },
                    text = {
                        TextField(
                            modifier = modifier.padding(top = 30.dp),
                            value = content.value,
                            onValueChange = { content.value = it },
                            singleLine = true
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Gray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    openDialog.value = false
                                }) {
                                Text("취소", color = Color.White)
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = PINK500),
                                onClick = {
                                    openDialog.value = false;
                                    //api 요청
                                    viewModel.updatePreset(clickNum.value, 2, content.value);
                                }) {
                                Text("설정")
                            }
                        }
                    }, shape = RoundedCornerShape(12.dp)
                )
            }


            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                for (num in 0..9) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                openDialog.value = true;
                                clickNum.value = num;
                                content.value =
                                    "${presetList[num] ?: ""}"
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(100),
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .background(
                                    brush = Brush.verticalGradient(listOf(BLUE500, PINK500)),
                                    shape = CircleShape,
                                    alpha = 0.7f
                                ),
                            elevation = null
                        ) {

                            Text(
                                text = "$num",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )


                        }

                        TextButton(modifier = modifier
                            .width(200.dp)
                            .height(50.dp), onClick = {
                            openDialog.value = true; clickNum.value = num; content.value =
                            "${presetList[num] ?: ""}"
                        }) {

                            Text(
                                text = "${presetList[num] ?: "미등록"}",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,

                                )


                        }
                    }
                }
            }
        }
    }
}
