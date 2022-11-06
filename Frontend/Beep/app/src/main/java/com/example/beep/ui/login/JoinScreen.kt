package com.example.beep.ui.login

import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.navigation.NavController

@Composable
fun JoinScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        join()
    }
}

@Composable
fun join(
) {
    var username by remember {
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
                username = username,
                password1 = password1,
                password2 = password2,
                onUsernameChange = {
                    username = it
                },
                onPassword1Change = {
                    password1 = it
                },
                onPassword2Change = {
                    password2 = it
                }
            ) {

            }
        }
    }
}

@Composable
fun joinFields(
    username: String, password1: String, password2: String,
    onUsernameChange: (String) -> Unit,
    onPassword1Change: (String) -> Unit,
    onPassword2Change: (String) -> Unit,
    onJoinClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "아이디(번호 입력)")
        DemoField(
            value = username,
            label = "Username",
            placeholder = "ex) 01012345678",
            onValueChange = onUsernameChange,
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

        TextButton(
            onClick = onJoinClick,
        ) {
            Text(text = "회원가입")
        }
    }

}