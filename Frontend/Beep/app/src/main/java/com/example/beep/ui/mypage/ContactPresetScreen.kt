package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.PresetViewModel
import com.example.beep.ui.message.UiState
@Composable
fun ContactPresetScreen(modifier: Modifier = Modifier, viewModel: MyPageViewModel, presetViewModel: PresetViewModel = viewModel()) {
    when (val currentUiState = presetViewModel.contactPreset) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            ContactPresetSuccessScreen(presetList = currentUiState.data)
        }
        is UiState.Error -> {
            ErrorScreen()
        }
    }
//    presetViewModel.getPresetByToken(2)
//    val contactList = presetViewModel.contactPreset
}

@Composable
fun ContactPresetSuccessScreen(modifier: Modifier = Modifier, presetList: Array<String?>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize(), horizontalAlignment = Alignment.Start
    ) {
//        Column(modifier = modifier.height(200.dp)) {
//            BeepForTest()
//        }

        Row(modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(20.dp)
            ,horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            TextButton(onClick = { }) {
                Text("<")
            }
            Text(modifier =modifier.padding(10.dp,0.dp,0.dp,0.dp) , text ="연락처 단축키 설정")
        }


        Column(modifier = modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.Start) {
            for (num in 0..9) {
                TextButton(modifier = modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                    Text(text = "$num : ${presetList[num]?:""}")
                }
            }
        }
    }
}

