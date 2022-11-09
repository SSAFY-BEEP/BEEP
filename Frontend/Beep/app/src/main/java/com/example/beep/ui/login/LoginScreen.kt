package com.example.beep.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.datatransport.cct.StringMerger

@Composable
fun LoginScreen() {
    var phoneNumber by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image58),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(6.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(
                    CutCornerShape(
                        topStart = 8.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(MaterialTheme.colors.background)
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            LoginHeader()

            Spacer(modifier = Modifier.height(120.dp))

            LoginFields(phoneNumber, password,
                onPhoneNumberChange = {
                    phoneNumber = it
                }, onPasswordChange = {
                    password = it
                }, onForgotPasswordClick = {

                })

        }
    }
}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welcome back", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            text = "Sign in to continue",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
fun LoginFields(
    phoneNumber: String, password: String,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "아이디")
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
            value = password,
            label = "Password",
            placeholder = "비밀번호를 입력",
            onValueChange = onPasswordChange,
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
            onClick = onForgotPasswordClick,
        ) {
            Text(text = "Forgot Password?")
        }

        LoginFooter(onSignUpClick = {}, phoneNumber = phoneNumber, password = password)
    }

}

@Composable
fun LoginFooter(
    onSignUpClick: () -> Unit,
    phoneNumber: String,
    password: String,
    viewModel: UserViewModel = viewModel()
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { viewModel.login(phoneNumber,password)}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "sign In")
        }
        TextButton(onClick = onSignUpClick) {
            Text(text = "Don't have an account, click here")
        }
    }
}

@Composable
fun DemoField(
    value: String,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}