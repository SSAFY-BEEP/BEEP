package com.example.beep.ui.dictionary

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.example.beep.data.dto.dictionary.DictionaryResponse
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.ui.theme.*

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClickMenu: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentUiState = viewModel.dictionaryState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {
                DictionarySuccessScreen(
                    dictionaryList = currentUiState.data,
                    viewModel = viewModel,
                    onClickMenu = onClickMenu
                )
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun DictionarySuccessScreen(
    dictionaryList: List<DictionaryResponse>,
    modifier: Modifier = Modifier,
    viewModel: DictionaryViewModel,
    onClickMenu: (String) -> Unit
) {
//    LaunchedEffect(key1 = viewModel.searchWord) {
//        viewModel.getDictionary()
//    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(69.dp)
                .drawBehind {
                    val borderSize = 2.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = GRAY100,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize
                    )
                }
        ) {
            Text(
                text = "삐삐 사전",
                modifier = Modifier
                    .padding(30.dp, 12.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
        SearchBar(viewModel)
        Column(modifier = Modifier.weight(4f)) {
            if (dictionaryList.isNotEmpty())
                DictionaryList(dictionaryList = dictionaryList)
            else {
                Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(text = "메시지가 없습니다.")
                }
            }
        }

    }
}

@Composable
fun SearchBar(
    viewModel: DictionaryViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding(8.dp)) {
        TextField(value = viewModel.searchWord, onValueChange = { viewModel.searchWord = it })
        Button(
            onClick = {
                if (viewModel.searchedState) {
                    viewModel.searchWord = ""
                    viewModel.getDictionary()
                    viewModel.searchedState = false
                } else {
                    if (viewModel.searchWord != "") viewModel.searchedState = true
                    viewModel.getDictionary()
                }
            }, modifier = modifier
                .padding(4.dp),
            colors = if (viewModel.searchedState) ButtonDefaults.buttonColors(
                backgroundColor = PINK500
            )
            else ButtonDefaults.buttonColors(backgroundColor = BLUE500)
        ) {
            if (!viewModel.searchedState) Text(text = "검색")
            else Text(text = "취소")
        }
    }
}

@Composable
fun DictionaryList(
    modifier: Modifier = Modifier,
    dictionaryList: List<DictionaryResponse>,
) {
    LazyColumn(modifier = modifier) {
        items(dictionaryList) {
            DictionaryItem(dictionary = it)
        }
    }
}

@Composable
fun DictionaryItem(
    dictionary: DictionaryResponse,
    modifier: Modifier = Modifier,
) {
    Card(elevation = 4.dp, modifier = modifier.padding(4.dp)) {
        Column {
            Row(
                modifier = modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = dictionary.word,
                            fontSize = 18.sp,
                            modifier = modifier.padding(4.dp)
                        )
                        Text(
                            text = dictionary.value,
                            fontSize = 14.sp,
                            modifier = modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}