package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.di.MainApplication
import com.example.beep.util.collectAsStateLifecycleAware

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), presetViewModel: PresetViewModel = viewModel()) {
//    val addressList = viewModel.exampleEntities.collectAsStateLifecycleAware(initial = listOf())
    val image = painterResource(R.drawable.bbibbi_blue)

    //프리셋가져오기
//    val presetList = presetViewModel.getPresetByToken()

//    val receiveMsg = homeViewModel.receiveMsg24.collectAsStateLifecycleAware(
//        initial = emptyList<BaseResponse<Message24Response>>()
//    );
//    val sendMsg = homeViewModel.sendMsg24.collectAsStateLifecycleAware(
//        initial = emptyList<BaseResponse<Message24Response>>()
//    );
//    Log.d("Message24 Receive", receiveMsg.value.toString())
//    Log.d("Message24 Send", sendMsg.value.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#F5F8FF")))
            .wrapContentSize(Center),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box {
            Image(
                painter = image,
                contentDescription = "삐삐 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .width(320.dp),
                contentScale = ContentScale.FillWidth
            )
            Bbibbi()

        }
        KeyboardVsAddressChoice()
    }
}







