package com.example.beep.ui.login

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JoinScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel = viewModel<UserViewModel>()
    val state = viewModel.authState
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is UserViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0XFFF5F8FF)
            )
            .clickable { keyboardController?.hide() },
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.phoneNumber,
                onValueChange = {
                    viewModel.onEvent(AuthFormEvent.PhoneNumberChanged(it))
                },
                isError = state.phoneNumberError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "phoneNumber")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )
            if(state.phoneNumberError != null) {
                Text(
                    text = state.phoneNumberError,
                    color = MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AuthFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if(state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)

                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.passwordCheck,
                onValueChange = {
                    viewModel.onEvent(AuthFormEvent.PasswordCheckChanged(it))
                },
                isError = state.passwordCheckError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "passwordCheck")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if(state.passwordCheckError != null) {
                Text(
                    text = state.passwordCheckError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)

                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.acceptedTerms,
                    onCheckedChange = {
                        viewModel.onEvent(AuthFormEvent.AcceptTerms(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Accept terms")
            }

            if(state.termError != null) {
                Text(
                    text = state.termError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)

                )
            }
            
            Button(
                onClick = {
                    viewModel.onEvent(AuthFormEvent.Submit)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Submit")
            }

        }

    }
}
