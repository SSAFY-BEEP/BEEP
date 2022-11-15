package com.example.beep.ui.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.beep.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.beep.MainActivity
import com.example.beep.di.MainApplication


@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel = viewModel<UserViewModel>()
    val loginState = viewModel.loginState
    val context = LocalContext.current
    val token = MainApplication.sharedPreferencesUtil.getToken()

    LaunchedEffect(loginState.isUserLoggedIn) {
        Log.d("launchEffect 실행", "$loginState")
        if (token != null) {
            if (token.isNotBlank()) {
                navController.navigate("beep_graph")
            }
        }
    }
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is UserViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(
                color = Color(0XFFF5F8FF)
            )
            .clickable { keyboardController?.hide() },
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp)
                .verticalScroll(scrollState)
                .imePadding()
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image58),
                contentDescription = "Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(6.dp)
                    .imePadding(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = loginState.loginPhoneNumber,
                onValueChange = {
                    viewModel.loginEvent(LoginFormEvent.LoginPhoneNumberChanged(it))
                },
                isError = loginState.loginPhoneNumberError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                placeholder = {
                    Text(text = "phoneNumber")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )

            if (loginState.loginPhoneNumberError != null) {
                Text(
                    text = loginState.loginPhoneNumberError,
                    color = MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = loginState.loginPassword,
                onValueChange = {
                    viewModel.loginEvent(LoginFormEvent.LoginPasswordChanged(it))
                },
                isError = loginState.loginPasswordError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                placeholder = {
                    Text(text = "password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.loginEvent(LoginFormEvent.Submit)
                        keyboardController?.hide()
                    }
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if (loginState.loginPasswordError != null) {
                Text(
                    text = loginState.loginPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.loginEvent(LoginFormEvent.Submit)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Submit")
            }

        }

    }
}