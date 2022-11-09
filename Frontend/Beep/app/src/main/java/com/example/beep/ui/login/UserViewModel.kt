package com.example.beep.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.auth.LoginRequest
import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.di.MainApplication
import com.example.beep.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*
import com.example.beep.ui.login.LoginFormEvent.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validatePasswordCheck: ValidatePasswordCheck = ValidatePasswordCheck(),
    private val validateTerms: ValidateTerms = ValidateTerms(),
    private val validateLoginPhoneNumber: ValidateLoginPhoneNumber = ValidateLoginPhoneNumber(),
    private val validateLoginPassword: ValidateLoginPassword = ValidateLoginPassword()
//    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    var loginState by mutableStateOf(LoginState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }
            is AuthFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is AuthFormEvent.PasswordCheckChanged -> {
                state = state.copy(passwordCheck = event.passwordCheck)
            }
            is AuthFormEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is AuthFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)
        val passwordCheckResult = validatePasswordCheck.execute(
            state.password,
            state.passwordCheck
        )
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            phoneNumberResult,
            passwordResult,
            passwordCheckResult,
            termsResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                phoneNumberError = phoneNumberResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                passwordCheckError = passwordCheckResult.errorMessage,
                termError = termsResult.errorMessage
            )
            return
        }
        val fcmToken = MainApplication.sharedPreferencesUtil.getFcmToken()

        val request =
            SignUpRequest(
                phoneNumber = state.phoneNumber,
                password = state.password,
                fcmToken = fcmToken!!
            )

        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(request).collectLatest {
                val requestLogin =
                    LoginRequest(phoneNumber = state.phoneNumber, password = state.password)
                loginUseCase.execute(requestLogin).collectLatest {
                    if (it.token.isNotBlank()) {
                        MainApplication.sharedPreferencesUtil.saveToken(it.token)
                    }
                }

            }
        }

    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    fun loginEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.LoginPhoneNumberChanged -> {
                loginState = loginState.copy(loginPhoneNumber = event.loginPhoneNumber)
            }
            is LoginFormEvent.LoginPasswordChanged -> {
                loginState = loginState.copy(loginPassword = event.loginPassword)
            }
            is Submit -> {
                submitLoginData()
            }
        }
    }

    private fun submitLoginData() {
        val loginPhoneNumberResult = validateLoginPhoneNumber.execute(loginState.loginPhoneNumber)
        val loginPasswordResult = validateLoginPassword.execute(loginState.loginPassword)

        val hasError = listOf(
            loginPhoneNumberResult,
            loginPasswordResult
        ).any { !it.successful }

        if(hasError) {
            loginState = loginState.copy(
                loginPhoneNumberError = loginPhoneNumberResult.errorMessage,
                loginPasswordError = loginPasswordResult.errorMessage
            )
            return
        }

        val request =
            LoginRequest(
                phoneNumber = loginState.loginPhoneNumber,
                password = loginState.loginPassword
            )

        viewModelScope.launch(Dispatchers.IO) {
                loginUseCase.execute(request).collectLatest {
                    if (it.token.isNotBlank()) {
                        MainApplication.sharedPreferencesUtil.saveToken(it.token)
                    }
                }
        }
    }

}



