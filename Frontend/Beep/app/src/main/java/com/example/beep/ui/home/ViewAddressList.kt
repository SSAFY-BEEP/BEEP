package com.example.beep.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.ui.mypage.BeepForTest
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.collectAsStateLifecycleAware



@Composable
fun ViewAddressList(
    modifier: Modifier = Modifier,
    viewModel: AddressViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val userAddressList: List<AddressResponse> by viewModel.exampleEntities.collectAsStateLifecycleAware(
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
            for (address in userAddressList) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "${address.name} : ${address.phone ?: ""}")
                }
            }
        }
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
    }
}