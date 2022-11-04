package com.example.beep.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R



@Composable
fun AddAddressSelf() {
    var amountInput by remember { mutableStateOf("") }
    var inputNameTxt by remember { mutableStateOf(TextFieldValue("")) }

    val nameMaxLength = 20

    Column(
        modifier = Modifier
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
    ) {
        Row() {
            Text(
                text = "이름",
                fontFamily = galmurinineFont
            )
            NameTextField(
                label = "이름",
                value = amountInput,
                onValueChange = { amountInput = it }
            )


        }
        Row() {
            Text(
                text = "연락처",
                fontFamily = galmurinineFont
            )
        }
        Row() {
            Text(
                text = "ssssssssssss"
            )
        }
    }
}


@Composable
fun NameTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(0.dp)
            .height(20.dp),
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
        )
    )
}
