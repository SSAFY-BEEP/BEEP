package com.example.beep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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


@Composable
fun ViewAddressList(
    modifier: Modifier = Modifier,
    viewModel: AddressViewModel = viewModel(),
    changeToAddAddress: () -> Unit,
    viewEditDelBtn: Boolean,
    isFromEdit: () -> Unit,

) {

    val scrollState = rememberScrollState()
    val userAddressList: List<AddressResponse> by viewModel.exampleEntities.collectAsStateLifecycleAware(
        initial = emptyList()
    )
//    val nameWeight = Modifier.weight(6f)

    Box(
        modifier = modifier
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .height(300.dp)
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
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "${address.phone ?: ""}",
                            fontSize = 14.sp,
                            fontFamily = galmurinineFont,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(9f)
                        )
                        if (viewEditDelBtn) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .width(18.dp)
                                    .weight(1f)
                                    .height(18.dp)
                                    .clickable {
                                        isFromEdit()
                                        changeToAddAddress()
                                    }
                                    .then(modifier)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.editicon),
                                    contentDescription = "수정",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .width(18.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .width(18.dp)
                                    .weight(1f)
                                    .height(18.dp)
                                    .clickable {
                                    }
                                    .then(modifier)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.deleteicon),
                                    contentDescription = "삭제",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .width(18.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = changeToAddAddress,
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .width(25.dp)
                .height(25.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(1F)),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp),

            ) {
            Text(
                text = "+",
                fontSize = 25.sp,
                fontFamily = galmurinineFont
            )
        }
//        Column(modifier = modifier.height(200.dp)) {
//            BeepForTest()
//        }
    }
}