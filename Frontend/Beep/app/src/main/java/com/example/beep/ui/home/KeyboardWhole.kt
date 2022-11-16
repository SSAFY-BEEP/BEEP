package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.mypage.ContactPresetSuccessScreen
import com.example.beep.ui.mypage.introduce.UiState


@ExperimentalComposeUiApi
@Composable
fun getKeyboard() {
    var isNum by remember { mutableStateOf(true) }


    if (isNum) {
        BeepNumKeyboard(
            changeIsNum = { isNum = !isNum }
        )
    } else {
        BeepAlphabetKeyboard(
            changeIsNum = { isNum = !isNum }
        )
    }
}




@ExperimentalComposeUiApi
@Composable
fun BeepNumKeyboard(
    changeIsNum: () -> Unit
) {
    val viewModel = viewModel<KeyboardViewModel>()
    val presetViewModel = viewModel<PresetViewModel>()
    val homeViewModel = viewModel<HomeViewModel>()


//    var goSearchPreset = false


    var presetList: Array<String?>

    fun doIt(clickNum: Int) {
        if (homeViewModel.currentPage == "PutAddress") {
            when (val currentUiState = presetViewModel.contactPreset) {
                is UiState.Loading -> {
                    Log.d("Preset", "Loading")
                }
                is UiState.Success -> {
                    Log.d("Preset", "Success")
//                    goSearchPreset = true
                    presetList = currentUiState.data
                    if (presetList[clickNum] != null) {
                        viewModel.onAction(KeyboardAction.Clear)
                        viewModel.onAction(KeyboardAction.Number(presetList[clickNum].toString()))
                    }
                }
                is UiState.Error -> {
                    Log.d("Preset", "Error")
                }
            }
        } else if (homeViewModel.currentPage == "PutMsg") {
            when (val currentUiState = presetViewModel.messagePreset) {
                is UiState.Loading -> {
                }
                is UiState.Success -> {
//                    goSearchPreset = true
                    presetList = currentUiState.data
                    Log.d("presetList", presetList[clickNum].toString())
                    if (presetList[clickNum] != null) {
                        viewModel.onAction(KeyboardAction.Clear)
                        viewModel.onAction(KeyboardAction.Number(presetList[clickNum].toString()))
                    }
                }
                is UiState.Error -> {
                }
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(300.dp)
            .width(320.dp)
            .padding(0.dp, 50.dp, 0.dp, 0.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,

    ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgone),
                onClick = { viewModel.onAction(KeyboardAction.Number("1")) },
                onLongClick = { doIt(clickNum = 1) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtwo),
                onClick = { viewModel.onAction(KeyboardAction.Number("2")) },
                onLongClick = { doIt(clickNum = 2) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgthree),
                onClick = { viewModel.onAction(KeyboardAction.Number("3")) },
                onLongClick = { doIt(clickNum = 3) }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgfour),
                onClick = { viewModel.onAction(KeyboardAction.Number("4")) },
                onLongClick = { doIt(clickNum = 4) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgfive),
                onClick = { viewModel.onAction(KeyboardAction.Number("5")) },
                onLongClick = { doIt(clickNum = 5) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgsix),
                onClick = { viewModel.onAction(KeyboardAction.Number("6")) },
                onLongClick = { doIt(clickNum = 6) }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgseven),
                onClick = { viewModel.onAction(KeyboardAction.Number("7")) },
                onLongClick = { doIt(clickNum = 7) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgeight),
                onClick = { viewModel.onAction(KeyboardAction.Number("8")) },
                onLongClick = { doIt(clickNum = 8) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgnine),
                onClick = { viewModel.onAction(KeyboardAction.Number("9")) },
                onLongClick = { doIt(clickNum = 9) }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtoalph),
                onClick = changeIsNum,
                onLongClick = {}
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgzero),
                onClick = { viewModel.onAction(KeyboardAction.Number("0")) },
                onLongClick = { doIt(clickNum = 0) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdelete),
                onClick = { viewModel.onAction(KeyboardAction.Delete) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
        }
    }
}




@ExperimentalComposeUiApi
@Composable
fun BeepAlphabetKeyboard(
    changeIsNum: () -> Unit,
) {
    val viewModel = viewModel<KeyboardViewModel>()
    val buttonSpacing = 13.dp

    val gArr = listOf('ㄱ'.toString(), 'ㅋ'.toString(), 'ㄲ'.toString())
    val nArr = listOf('ㄴ'.toString(), 'ㄹ'.toString())
    val dArr = listOf('ㄷ'.toString(), 'ㅌ'.toString(), 'ㄸ'.toString())
    val bArr = listOf('ㅂ'.toString(), 'ㅍ'.toString(), 'ㅃ'.toString())
    val sArr = listOf('ㅅ'.toString(), 'ㅎ'.toString(), 'ㅆ'.toString())
    val jArr = listOf('ㅈ'.toString(), 'ㅊ'.toString(), 'ㅉ'.toString())
    val yArr = listOf('ㅇ'.toString(), 'ㅁ'.toString())


    var gCount by remember { mutableStateOf(0) }
    var nCount by remember { mutableStateOf(0) }
    var dCount by remember { mutableStateOf(0) }
    var bCount by remember { mutableStateOf(0) }
    var sCount by remember { mutableStateOf(0) }
    var jCount by remember { mutableStateOf(0) }
    var yCount by remember { mutableStateOf(0) }
    var spaceCount by remember { mutableStateOf(1) }


    fun letsGoNumber(currentAlphabet: Char) {
        if(currentAlphabet == 'ㄱ') {
            if(gCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                nCount = 0
                dCount = 0
                bCount = 0
                sCount = 0
                jCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(gArr[gCount % 3]))
            }
            gCount += 1
        }
        else if(currentAlphabet == 'ㄴ') {
            if(nCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                dCount = 0
                bCount = 0
                sCount = 0
                jCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(nArr[nCount % 2]))
            }
            nCount += 1
        }
        else if(currentAlphabet == 'ㄷ') {
            if(dCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                nCount = 0
                bCount = 0
                sCount = 0
                jCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(dArr[dCount % 3]))
            }
            dCount += 1
        }
        else if(currentAlphabet == 'ㅂ') {
            if(bCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                nCount = 0
                dCount = 0
                sCount = 0
                jCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(bArr[bCount % 3]))
            }
            bCount += 1
        }
        else if(currentAlphabet == 'ㅅ') {
            if(sCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                nCount = 0
                dCount = 0
                bCount = 0
                jCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(sArr[sCount % 3]))
            }
            sCount += 1
        }
        else if(currentAlphabet == 'ㅈ') {
            if(jCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                nCount = 0
                dCount = 0
                bCount = 0
                sCount = 0
                yCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(jArr[jCount % 3]))
            }
            jCount += 1
        }
        else if(currentAlphabet == 'ㅇ') {
            if(yCount == 0) {
                viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
                gCount = 0
                nCount = 0
                dCount = 0
                bCount = 0
                sCount = 0
                jCount = 0
                spaceCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Change(yArr[yCount % 2]))
            }
            yCount += 1
        }
        else if(currentAlphabet == 'n') {
            gCount = 0
            nCount = 0
            dCount = 0
            bCount = 0
            sCount = 0
            jCount = 0
            yCount = 0
            spaceCount = 1
        }
        else if(currentAlphabet == ' ') {
            if(spaceCount == 0) {
                gCount = 0
                nCount = 0
                dCount = 0
                bCount = 0
                sCount = 0
                jCount = 0
                yCount = 0
            } else {
                viewModel.onAction(KeyboardAction.Number(' '.toString()))
            }
            spaceCount = 1
        }
        else {
            viewModel.onAction(KeyboardAction.Number(currentAlphabet.toString()))
            gCount = 0
            nCount = 0
            dCount = 0
            bCount = 0
            sCount = 0
            jCount = 0
            yCount = 0
            spaceCount = 1
        }
    }


    Column(
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(300.dp)
            .padding(40.dp, 40.dp, 40.dp, 20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgheart),
                onClick = { letsGoNumber('♥') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgstar),
                onClick = { letsGoNumber('★') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgspacebar),
                onClick = { letsGoNumber(' ') },
                onLongClick = {  }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimggieuk),
                onClick = { letsGoNumber('ㄱ') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgnieun),
                onClick = { letsGoNumber('ㄴ') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdiguet),
                onClick = { letsGoNumber('ㄷ') },
                onLongClick = {  }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgbieup),
                onClick = { letsGoNumber('ㅂ') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgsieot),
                onClick = { letsGoNumber('ㅅ') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgjieut),
                onClick = { letsGoNumber('ㅈ') },
                onLongClick = {  }
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtonum),
                onClick = {
                    letsGoNumber('n')
                    changeIsNum()
                          },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgyieung),
                onClick = { letsGoNumber('ㅇ') },
                onLongClick = {  }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdelete),
                onClick = {
                    letsGoNumber('n')
                    viewModel.onAction(KeyboardAction.Delete)
                          },
                onLongClick = {  }
            )
        }
    }
}