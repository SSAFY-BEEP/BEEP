package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.PresetViewModel
import com.example.beep.ui.message.UiState
import com.example.beep.ui.savedmessage.SavedMessageSuccessScreen
import androidx.lifecycle.viewmodel.compose.viewModel
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
    presetViewModel.getPresetByToken(2)
    val contactList = presetViewModel.contactPreset
}

@Composable
fun ContactPresetSuccessScreen(modifier: Modifier = Modifier, presetList: Array<String?>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize(), horizontalAlignment = Alignment.Start
    ) {
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
        Column(modifier = modifier
            .height(400.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.Start) {
            for (num in 0L..9L) {
                TextButton(modifier = modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                    Text(text = "$num : ${presetList[0]}")
                }
            }
        }
    }
}

