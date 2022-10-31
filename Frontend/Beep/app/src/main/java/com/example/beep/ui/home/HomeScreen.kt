package com.example.beep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BeepWithKeyboard()
    }
}
@Composable
fun BeepWithKeyboard() {
    val image = painterResource(R.drawable.bbibbi_white)
    Column {
        Box(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = image,
                contentDescription = "삐삐 이미지",
                modifier = Modifier
                    .width(360.dp),
//                    .wrapContentWidth(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )

        }
        Text(
            text = "주소록",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp),
            fontSize = 16.sp,
        )
        BeepKeyboard()
    }

}


@Composable
fun BeepKeyboard() {

    val btnDelete = painterResource(com.example.beep.R.drawable.btnimgdelete)

    val btnOne = painterResource(R.drawable.btnimgone)
    val btnTwo = painterResource(R.drawable.btnimgtwo)
    val btnThree = painterResource(R.drawable.btnimgthree)
    val btnFour = painterResource(R.drawable.btnimgfour)
    val btnFive = painterResource(R.drawable.btnimgfive)
    val btnSix = painterResource(R.drawable.btnimgsix)
    val btnSeven = painterResource(R.drawable.btnimgseven)
    val btnEight = painterResource(R.drawable.btnimgeight)
    val btnNine = painterResource(R.drawable.btnimgnine)
    val btnZero = painterResource(R.drawable.btnimgzero)
    val btnToAlphabet = painterResource(R.drawable.btnimgtoalph)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = btnOne,
                contentDescription = stringResource(R.string.btn_description_one),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnTwo,
                contentDescription = stringResource(R.string.btn_description_two),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnThree,
                contentDescription = stringResource(R.string.btn_description_three),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = btnFour,
                contentDescription = stringResource(R.string.btn_description_four),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnFive,
                contentDescription = stringResource(R.string.btn_description_five),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnSix,
                contentDescription = stringResource(R.string.btn_description_six),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = btnSeven,
                contentDescription = stringResource(R.string.btn_description_seven),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnEight,
                contentDescription = stringResource(R.string.btn_description_eight),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnNine,
                contentDescription = stringResource(R.string.btn_description_nine),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = btnToAlphabet,
                contentDescription = stringResource(R.string.btn_description_to_alphabet),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnZero,
                contentDescription = stringResource(R.string.btn_description_zero),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
            Image(
                painter = btnDelete,
                contentDescription = stringResource(R.string.btn_description_delete),
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        onClick = {

                        }
                    )
                    .padding(5.dp)
            )
        }


    }
}