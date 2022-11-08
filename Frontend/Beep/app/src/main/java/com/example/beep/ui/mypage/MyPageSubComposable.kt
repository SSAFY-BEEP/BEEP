package com.example.beep.ui.mypage

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TestComposable(viewModel: MyPageViewModel = viewModel()) {
    println("!!!!!!!!!!!!!!!!!!!!!!!${viewModel.testValue}")
    viewModel.printTestValue()
}