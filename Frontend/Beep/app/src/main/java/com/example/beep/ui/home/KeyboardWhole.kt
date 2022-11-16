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
    val presetViewModel = viewModel<PresetViewModel>()
    val homeViewModel = viewModel<HomeViewModel>()

    if (homeViewModel.currentPage == "PutAddress") {

    } else if (homeViewModel.currentPage == "PutMsg") {

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
                paint = painterResource(R.drawable.btnimgone),
                onClick = { viewModel.onAction(KeyboardAction.Number("1")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgtwo),
                onClick = { viewModel.onAction(KeyboardAction.Number("2")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgthree),
                onClick = { viewModel.onAction(KeyboardAction.Number("3")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
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
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgfive),
                onClick = { viewModel.onAction(KeyboardAction.Number("5")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgsix),
                onClick = { viewModel.onAction(KeyboardAction.Number("6")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
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
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgeight),
                onClick = { viewModel.onAction(KeyboardAction.Number("8")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
            )
            KeyboardButton(
                paint = painterResource(R.drawable.btnimgnine),
                onClick = { viewModel.onAction(KeyboardAction.Number("9")) },
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
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
                onLongClick = { viewModel.onAction(KeyboardAction.Clear) }
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
                paint = painterResource(R.drawable.btnimgdigeut),
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