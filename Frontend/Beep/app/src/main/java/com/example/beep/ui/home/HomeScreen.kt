package com.example.beep.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    presetViewModel: PresetViewModel = viewModel(),
) {
    val image = painterResource(R.drawable.bbibbi_blue)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        presetViewModel.getPresetByToken(1)
        presetViewModel.getPresetByToken(2)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Center)
            .verticalScroll(scrollState),

    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box {
                Image(
                    painter = image,
                    contentDescription = "삐삐 이미지",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .width(320.dp),
                    contentScale = ContentScale.FillWidth
                )
                Bbibbi()

            }
            KeyboardVsAddressChoice()
        }
    }

}







