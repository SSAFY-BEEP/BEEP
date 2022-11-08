package com.example.beep.ui.login

import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.di.MainApplication


@Composable
fun JoinScreen() {
    var phoneNumber by remember {
        mutableStateOf("")
    }

    var password1 by remember {
        mutableStateOf("")
    }

    var password2 by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0XFFF5F8FF)
            )
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            joinFields(
                phoneNumber = phoneNumber,
                password1 = password1,
                password2 = password2,

                onPhoneNumberChange = {
                    phoneNumber = it
                },
                onPassword1Change = {
                    password1 = it
                },
                onPassword2Change = {
                    password2 = it
                }
            )

        }
    }
}

@Composable
fun joinFields(
    phoneNumber: String,
    password1: String,
    password2: String,
    onPhoneNumberChange: (String) -> Unit,
    onPassword1Change: (String) -> Unit,
    onPassword2Change: (String) -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    Column {
        Text(text = "아이디(번호 입력)")

        DemoField(
            value = phoneNumber,
            label = "phoneNumber",
            placeholder = "ex) 01012345678",
            onValueChange = onPhoneNumberChange,
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "비밀번호")

        DemoField(
            value = password1,
            label = "Password",
            placeholder = "비밀번호를 입력하세요",
            onValueChange = onPassword1Change,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Text(text = "비밀번호 재확인")

        Spacer(modifier = Modifier.height(8.dp))

        DemoField(
            value = password2,
            label = "Password",
            placeholder = "비밀번호를 입력하세요",
            onValueChange = onPassword2Change,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitBtn(phoneNumber = phoneNumber, password1 = password1, password2 = password2)

    }

}

@Composable
fun SubmitBtn(
    phoneNumber: String,
    password1: String,
    password2: String,
    viewModel: UserViewModel = viewModel()
) {
    Button(onClick = {
        Log.d("phoneNumber", phoneNumber)
        Log.d("password", password1)
        Log.d("passwordCheck", password2)
        viewModel.signUp(phoneNumber, password1, password2)}) {
        Text(text = "회원가입")
    }
}