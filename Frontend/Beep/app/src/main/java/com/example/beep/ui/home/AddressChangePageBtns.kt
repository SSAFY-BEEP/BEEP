package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.ui.theme.BLUE500
import com.example.beep.util.collectAsStateLifecycleAware
import kotlinx.coroutines.*


@Composable
fun AddCancelBtn(
    changeToAddAddress: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { changeToAddAddress() }
            .height(40.dp)
            .width(100.dp)
            .padding(20.dp, 0.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp, color = Color(android.graphics.Color.parseColor("#7AA8FF")),
                shape = RoundedCornerShape(10.dp))
            .background(Color.White)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "취소",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color(android.graphics.Color.parseColor("#7AA8FF"))
        )
    }
}

@Composable
fun AddToBookBtn() {
    Button(
        onClick = { /*showMessage*/ },
        modifier = Modifier
            .height(40.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                android.graphics.Color.parseColor(
                    "#7AA8FF"
                )
            )
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),

        ) {
        Text(
            text = "주소록",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun AddSubmitBtn(
    name: String,
    phone: String,
    viewModel: AddressPostSelfViewModel = viewModel(),
    getViewModel: AddressViewModel = viewModel(),
    changeToAddAddress: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false)  }
//    var dialogTxt = ""
    val dialogTxt = remember { mutableStateOf("")  }
//    val userAddressList: List<AddressResponse> by getViewModel.exampleEntities.collectAsStateLifecycleAware(
//        initial = emptyList()
//    )



    Button(
        onClick = {
            Log.d("PHONE", phone)
            Log.d("NAME", name)

            if (name.isEmpty()) {
                openDialog.value = true
                dialogTxt.value = "이름을 입력해주세요"
            } else if (phone.isEmpty()) {
                openDialog.value = true
                dialogTxt.value = "핸드폰 번호를 입력해주세요"
            } else if (phone.length != 11) {
                openDialog.value = true
                dialogTxt.value = "유효한 번호를 입력해주세요"
            } else {
                Log.d("POST 실행!!!!!!!!!!!", name)
                postDo(
                    name = name,
                    phone = phone,
                    changeToAddAddress = changeToAddAddress,
                    postUsingViewModel = { phone: String, name: String ->
                        viewModel.postAddress(
                            phone,
                            name
                        )
                        GlobalScope.launch {
                            delay(100L)
                            getViewModel.getAddress()
                        }
                    },
                )
            }
        },
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            ,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                android.graphics.Color.parseColor(
                    "#7AA8FF"
                )
            )
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),
    ) {
        Text(
            text = "등록",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color.White
        )
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            text = { Text(
                text = dialogTxt.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )},
            confirmButton = {
                Button(
                    onClick = { openDialog.value = false },
                    colors = ButtonDefaults.buttonColors(BLUE500),
                    modifier = Modifier
                        .width(70.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "확인",
                        color = Color.White
                    )
                }
            }
        )
    }
}

fun postDo(
    name: String,
    phone: String,
    changeToAddAddress: () -> Unit,
    postUsingViewModel: (String, String) -> Unit,
) {
    runBlocking { postUsingViewModel(phone, name) }
    changeToAddAddress()
}


@Composable
fun PatchSubmitBtn(
    apiPhone: String,
    name: String,
    phone: String,
    changeToPatchAddress: () -> Unit,
    viewModel: AddressPatchViewModel = viewModel(),
    getViewModel: AddressViewModel = viewModel(),
) {
    val openDialog = remember { mutableStateOf(false)  }
//    var dialogTxt = ""
    val dialogTxt = remember { mutableStateOf("")  }
//    var goPatch = false

    Button(
        onClick = {
            if (name.isEmpty()) {
                openDialog.value = true
                dialogTxt.value = "이름을 입력해주세요"
            } else if (phone.isEmpty()) {
                openDialog.value = true
                dialogTxt.value = "핸드폰 번호를 입력해주세요"
            } else if (phone.length != 11) {
                openDialog.value = true
                dialogTxt.value = "유효한 번호를 입력해주세요"
            } else {
                patchDo(
                    apiPhone = apiPhone,
                    name = name,
                    phone = phone,
                    changeToPatchAddress = changeToPatchAddress,
                    patchUsingViewModel = { apiPhone: String, phone: String, name: String ->
                        viewModel.patchAddress(
                            apiPhone,
                            phone,
                            name
                        )
                        GlobalScope.launch {
                            delay(100L)
                            getViewModel.getAddress()
                        }
                    },
                )
            }
        },
        modifier = Modifier
            .height(40.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                android.graphics.Color.parseColor(
                    "#7AA8FF"
                )
            )
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp, 0.dp),
    ) {
        Text(
            text = "수정",
            fontFamily = galmurinineFont,
            fontSize = 16.sp,
            color = Color.White
        )
    }
    if (openDialog.value) {

        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
                openDialog.value = false
            },
//            text = { dialogTxt.value },
            text = { Text(
                text = dialogTxt.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )},
            confirmButton = {
                Button(
                    onClick = { openDialog.value = false },
                    colors = ButtonDefaults.buttonColors(BLUE500),
                    modifier = Modifier
                        .width(70.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "확인",
                        color = Color.White
                    )
                }
            }
        )
    }
}

fun patchDo(
    apiPhone: String,
    name: String,
    phone: String,
    changeToPatchAddress: () -> Unit,
    patchUsingViewModel: (String, String, String) -> Unit,
) {
    runBlocking { patchUsingViewModel(apiPhone, phone, name) }
    changeToPatchAddress()
}



//@Composable
//fun showDialogue(
//    openDialog: Boolean,
//    dialogTxt: String,
//    closeDialog: () -> Unit
//) {
//    if (openDialog) {
//
//        AlertDialog(
//            onDismissRequest = {
//                closeDialog
//            },
//            text = { dialogTxt },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        closeDialog
//                    }) {
//                    Text("OK")
//                }
//            }
//        )
//    }
//}



