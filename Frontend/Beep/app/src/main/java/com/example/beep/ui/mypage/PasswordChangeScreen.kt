package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.ui.home.getKeyboard

enum class PasswordChangeState {
    Previous, New, CheckNew
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordChangeScreen(modifier: Modifier = Modifier, viewModel: MyPageViewModel) {
    var currentUiState by remember { mutableStateOf(PasswordChangeState.Previous) }
    var previousPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordCheck by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (currentUiState) {
            PasswordChangeState.Previous -> {
                CheckPreviousFragment(previousPassword) { newState: PasswordChangeState ->
                    currentUiState = newState
                }
            }
            PasswordChangeState.New -> {
                EnterNewPasswordFragment(newPassword) { newState: PasswordChangeState ->
                    currentUiState = newState
                }
            }
            PasswordChangeState.CheckNew -> {
                CheckNewPasswordFragment(newPasswordCheck) { newState: PasswordChangeState ->
                    currentUiState = newState
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckNewPasswordFragment(
    newPasswordCheck: String,
    changeUiState: (PasswordChangeState) -> Unit
) {
        Text(text = "새 비밀번호 확인", fontSize = 24.sp)
        BeepForTest(text = if (newPasswordCheck == "") "새 비밀번호를 한번 더 입력해주세요" else newPasswordCheck)
        CancelConfirmButtons(
            onConfirmBtnClicked = { /* 비밀번호 변경 HTTP 요청 */ },
            onCancelBtnClicked = { changeUiState(PasswordChangeState.New) })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EnterNewPasswordFragment(newPassword: String, changeUiState: (PasswordChangeState) -> Unit) {
    Text(text = "새 비밀번호 입력", fontSize = 24.sp)
    BeepForTest(text = if (newPassword == "") "변경할 비밀번호를 입력해주세요" else newPassword)
//    getKeyboard()
    CancelConfirmButtons(
        onConfirmBtnClicked = { changeUiState(PasswordChangeState.CheckNew) },
        onCancelBtnClicked = { changeUiState(PasswordChangeState.Previous) })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckPreviousFragment(password: String, changeUiState: (PasswordChangeState) -> Unit) {
    Text(text = "기존 비밀번호 확인", fontSize = 24.sp)
    BeepForTest(text = if (password == "") "비밀번호를 입력해주세요" else password)
//    getKeyboard()
    CancelConfirmButtons(
        onConfirmBtnClicked = { changeUiState(PasswordChangeState.New) },
        onCancelBtnClicked = { /* 마이페이지 메인으로 돌아가는 코드 */})
}

@Composable
fun CancelConfirmButtons(
    modifier: Modifier = Modifier,
    onCancelBtnClicked: () -> Unit,
    onConfirmBtnClicked: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { onCancelBtnClicked() }) {
            Text(text = "취소")
        }
        Button(onClick = { onConfirmBtnClicked() }) {
            Text(text = "확인")
        }
    }
}