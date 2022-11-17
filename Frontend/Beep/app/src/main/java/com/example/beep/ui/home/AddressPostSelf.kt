package com.example.beep.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddressPostSelf(
    changeToAddAddress: () -> Unit,
) {
    var inputNameTxt by remember { mutableStateOf("") }
    var inputNumberTxt by remember { mutableStateOf("") }
//    var inputNumberTxt by remember { mutableStateOf(TextFieldValue("")) }

    val nameMaxLength = 20

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp, 20.dp, 10.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
//                .height(40.dp)
        ) {
            NameTextField(
                value = inputNameTxt,
                onValueChange = { inputNameTxt = it },
            )
        }
        Row(
            modifier = Modifier
        ) {
            NumberIntField(
                value = inputNumberTxt,
                onValueChange = { inputNumberTxt = it }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AddCancelBtn(changeToAddAddress)
//            AddToBookBtn()

            AddSubmitBtn(
                name = inputNameTxt,
                phone = inputNumberTxt,
                changeToAddAddress=changeToAddAddress
            )
        }
    }
}


@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    
) {
    OutlinedTextField(
        label = { Text(text = "이름")},
        value = value,
        onValueChange = onValueChange,
//        modifier = Modifier
//            .height(120.dp),
        singleLine = true,
        placeholder = { Text(
            text = "이름을 입력해주세요",
            fontFamily = galmurinineFont
        ) },
        textStyle = TextStyle(
            fontFamily = galmurinineFont,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor("#7AA8FF")),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor("#9DBFFF"))
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}

@ExperimentalComposeUiApi
@Composable
fun NumberIntField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        label = { Text(text = "연락처")},
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text(
            text = "연락처을 입력해주세요",
            fontFamily = galmurinineFont
        ) },
        textStyle = TextStyle(
            fontFamily = galmurinineFont,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor("#7AA8FF")),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor("#9DBFFF"))
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()}
        )
    )
}
