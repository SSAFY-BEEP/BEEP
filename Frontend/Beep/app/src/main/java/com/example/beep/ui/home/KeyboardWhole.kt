package com.example.beep.ui.home

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
    val buttonSpacing = 11.dp
//    val colorBackground = painterResource(R.drawable.btnimgtonum)

    Column(
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(40.dp, 20.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgone),
//                symbol = "1",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("1"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtwo),
//                symbol = "2",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("2"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgthree),
//                symbol = "3",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("3"))
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgfour),
//                symbol = "4",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("4"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgfive),
//                symbol = "5",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("5"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgsix),
//                symbol = "6",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("6"))
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgseven),
//                symbol = "7",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("7"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgeight),
//                symbol = "8",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("8"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgnine),
//                symbol = "9",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("9"))
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtoalph),
//                symbol = "ㄱㄴㄷ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .clickable(
                        onClick = changeIsNum
                    )
            ) {
//                viewModel.onAction(KeyboardAction.Number("ㄱㄴㄷ"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgzero),
//                symbol = "0",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Number("0"))
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdelete),
//                symbol = "<",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                viewModel.onAction(KeyboardAction.Delete)
            }
        }
    }
}




@ExperimentalComposeUiApi
@Composable
fun BeepAlphabetKeyboard(
    changeIsNum: () -> Unit,
) {
    val viewModel = viewModel<KeyboardViewModel>()
    val buttonSpacing = 11.dp

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
            .padding(40.dp, 20.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgheart),
//                symbol = "♥",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('♥')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgstar),
//                symbol = "★",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('★')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgspacebar),
//                description = "1",
//                symbol = "﹈",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber(' ')
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimggieuk),
//                symbol = "ㄱㅋ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㄱ')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgnieun),
//                symbol = "ㄴㄹ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㄴ')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdigeut),
//                symbol = "ㄷㅌ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㄷ')
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgbieup),
//                symbol = "ㄱ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㅂ')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgsieot),
//                symbol = "ㄴ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㅅ')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgjieut),
//                symbol = "ㄷ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㅈ')
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtonum),
//                symbol = "123",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .clickable(
                        onClick = changeIsNum
                    )
            ) {
                letsGoNumber('n')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgyieung),
//                symbol = "ㅇ",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('ㅇ')
            }
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgdelete),
//                symbol = "<",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                letsGoNumber('n')
                viewModel.onAction(KeyboardAction.Delete)
            }
        }
    }
}