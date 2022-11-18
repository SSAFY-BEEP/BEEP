package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.ui.message.MessagePopupState
import com.example.beep.ui.mypage.introduce.UiState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AddressListContent(
    modifier: Modifier = Modifier,
    viewModel: AddressViewModel = viewModel(),
    keyboardViewModel: KeyboardViewModel = viewModel(),
    viewModelDelete: AddressDeleteViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    viewEditDelBtn: Boolean,
    changeToAddAddress: () -> Unit,
    changeToPatchAddress: () -> Unit,
    changeDefaultNameString: (String) -> Unit,
    changeDefaultPhoneString: (String) -> Unit,
    showAddressToggle: () -> Unit
) {


    var themeColorBlue = Color(android.graphics.Color.parseColor("#7AA8FF"))
    val scrollState = rememberScrollState()

    var phoneWeight = if (viewEditDelBtn) {
        7
    } else {
        9
    }


    Box(
        modifier = modifier
            .padding(10.dp, 5.dp, 10.dp, 5.dp)
            .height(232.dp)
            .width(320.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .height(232.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            when (val currentUiState = viewModel.addressListUiState) {
                is UiState.Loading -> {
                    Text(
                        text = "로딩중...",
                        fontSize = 14.sp,
                        fontFamily = galmurinineFont,
                        color = Color.Black,
                        modifier = Modifier
                            .weight((phoneWeight as Number).toFloat()),
                    )
                }
                is UiState.Success -> {
                    for (address in currentUiState.data) {
                        var showDialog by remember { mutableStateOf(false) }
                        TextButton(
                            onClick = {
                                showDialog = true
                                Log.d("Selected Address", address.phone)
                            },
                            modifier = Modifier
                                .height(40.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = address.name ?: "",
                                    fontSize = 14.sp,
                                    fontFamily = galmurinineFont,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .weight(6f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = address.phone ?: "",
                                    fontSize = 14.sp,
                                    fontFamily = galmurinineFont,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .weight((phoneWeight as Number).toFloat()),
                                )
                                if(showDialog)
                                    ConfirmDialog(address, keyboardViewModel, homeViewModel) {
                                        showDialog = false
                                        showAddressToggle()
                                    }
                                if (viewEditDelBtn) {
                                    Row(
                                        modifier = Modifier
                                            .weight(2f)
                                            .wrapContentHeight(Alignment.CenterVertically)
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .width(16.dp)
                                                .height(16.dp)
                                                .clickable {
                                                    changeDefaultNameString(address.name)
                                                    changeDefaultPhoneString(address.phone)
                                                    changeToPatchAddress()
                                                }
                                                .then(modifier)
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.editicon),
                                                contentDescription = "수정",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(Alignment.CenterVertically)
                                                    .width(16.dp),
                                                contentScale = ContentScale.FillWidth
                                            )
                                        }
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(18.dp))
                                                .width(16.dp)
                                                .height(16.dp)
                                                .clickable {
                                                    viewModelDelete.deleteAddress(address.phone)
                                                    GlobalScope.launch {
                                                        delay(100L)
                                                        viewModel.getAddress()
                                                    }
                                                }
                                                .then(modifier)
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.deleteicon),
                                                contentDescription = "삭제",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(Alignment.CenterVertically)
                                                    .width(16.dp),
                                                contentScale = ContentScale.FillWidth
                                            )
                                        }
                                    }
                                } else {
                                    Box() {

                                    }
                                }
                            }
                        }
                    }
                    Log.d("데이터", currentUiState.data.toString())
                }
                is UiState.Error -> {
                    Text(
                        text = "ERROR",
                        fontSize = 14.sp,
                        fontFamily = galmurinineFont,
                        color = Color.Black,
                        modifier = Modifier
                            .weight((phoneWeight as Number).toFloat()),
                    )
                }
            }

        }
        Button(
            onClick = changeToAddAddress,
            modifier = Modifier
                .offset(260.dp, 162.dp)
                .width(28.dp)
                .height(28.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = themeColorBlue),
            shape = RoundedCornerShape(14.dp),
            contentPadding = PaddingValues(0.dp),

            ) {
            Image(
                painter = painterResource(id = R.drawable.pluswhite),
                contentDescription = "추가하기",
                modifier = Modifier
                    .width(18.dp)
            )
        }
//        Column(modifier = modifier.height(200.dp)) {
//            BeepForTest()
//        }
    }
}

@Composable
fun ConfirmDialog(
    address: AddressResponse,
    keyboardViewModel: KeyboardViewModel,
    homeViewModel: HomeViewModel,
    showDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showDialog() },
        title = {
            Text(
                text = "${address.phone} 번호를 선택하셨습니다."
            )
        },
        text = {
            Text(
                text = "이 번호로 메시지를 보내시겠습니까?"
            )
        },
        confirmButton = {
            TextButton(onClick = {
                homeViewModel.setMessageReceiverNum(address.phone)
                homeViewModel.currentPage = "PutMsg"
                showDialog()
            }) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog() }) {
                Text("취소")
            }
        }
    )
}