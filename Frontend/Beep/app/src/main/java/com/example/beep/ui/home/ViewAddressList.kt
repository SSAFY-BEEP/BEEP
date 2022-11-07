package com.example.beep.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.ui.mypage.BeepForTest

//@Composable
//fun ViewAddressList() {
//    Column(
//        modifier = Modifier
//            .padding(20.dp, 10.dp, 20.dp, 10.dp)
//    ) {
//        Row() {
//            Text(text = "ssssssssssss")
//        }
//        Row() {
//            Text(text = "ssssssssssss")
//        }
//        Row() {
//            Text(text = "ssssssssssss")
//        }
//
//    }
//}

@Composable
fun ViewAddressList(
    modifier: Modifier,
    viewModel: AddressViewModel
) {
    val scrollState = rememberScrollState()
    val userMessagePresetList: List<AddressResponse> by viewModel.exampleEntities.collectAsStateLifecycleAware(
        initial = emptyList()
    )
    Column(
        modifier = modifier
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .height(400.dp)
                .verticalScroll(scrollState)
        ) {
            for (preset in userMessagePresetList) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "${preset.name} : ${preset.phone ?: ""}")
                }
            }
//            for (num in 0L..9L) {
//                TextButton(onClick = { /*TODO*/ }) {
//                    Text(text = "$num : ${messagePresetList[num]?.content ?: ""}")
//                }
//            }
        }
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
    }



}