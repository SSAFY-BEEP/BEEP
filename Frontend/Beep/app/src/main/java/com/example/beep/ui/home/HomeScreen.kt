package com.example.beep.ui.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.BeepImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(),
    presetViewModel: PresetViewModel = viewModel(),
) {

    val selectBeepImage = MainApplication.sharedPreferencesUtil.getTheme()
    val engrave = MainApplication.sharedPreferencesUtil.getEngrave()
    val scrollState = rememberScrollState()
    val vibrationPermissionState = rememberPermissionState(
        Manifest.permission.VIBRATE
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        presetViewModel.getPresetByToken(1)
        presetViewModel.getPresetByToken(2)
        if (!vibrationPermissionState.status.isGranted) {
            vibrationPermissionState.launchPermissionRequest()
        }
        homeViewModel.toastMessage.collect {
            message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Center)
            .verticalScroll(scrollState),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(320.dp)
                .padding(0.dp, 10.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box {

                BeepImage(Modifier,"",selectBeepImage)
                Bbibbi()

            }
            KeyboardVsAddressChoice()
        }
    }

}







