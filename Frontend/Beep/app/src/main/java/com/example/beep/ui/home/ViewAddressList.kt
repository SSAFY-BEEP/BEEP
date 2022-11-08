package com.example.beep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.ui.mypage.BeepForTest
import com.example.beep.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.collectAsStateLifecycleAware


@Preview
@Composable
fun ViewAddressList(
    modifier: Modifier = Modifier,
    viewModel : AddressViewModel = viewModel()
) {

    val scrollState = rememberScrollState()
    val userAddressList: List<AddressResponse> by viewModel.exampleEntities.collectAsStateLifecycleAware(
        initial = emptyList()
    )
    Column(
        modifier = modifier
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .height(400.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            for (address in userAddressList) {
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        Text(
                            text = "${address.name ?: ""}",
                            fontSize = 14.sp,
                            fontFamily = galmurinineFont,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(6f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = "${address.phone ?: ""}",
                            fontSize = 14.sp,
                            fontFamily = galmurinineFont,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(9f)
                        )
                        Image(
                            painter = painterResource(R.drawable.send),
                            contentDescription = "삐삐 이미지",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(Alignment.CenterVertically)
                                .width(18.dp)
                                .weight(1f),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
    }
}