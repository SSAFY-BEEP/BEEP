package com.example.beep.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.BACKGROUND_WHITE
import com.example.beep.ui.theme.GRAY100

@Composable
fun MyPageScreen(onClickMenu: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
    ) {
        Column(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp)
                    .drawBehind {
                        val borderSize = 2.dp.toPx()
                        val y = size.height - borderSize / 2
                        drawLine(
                            color = GRAY100,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = borderSize
                        )
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "설정",
                    modifier = Modifier
                        .padding(20.dp, 10.dp, 10.dp, 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "설정",
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                CustomTextTitle("단축키 설정")
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CustomText1({ onClickMenu("contactPreset") }, "연락처 단축키 설정")
                    CustomText1({ onClickMenu("messagePreset") }, "메시지 단축키 설정")
                }

                CustomTextTitle("인사말 설정")

                CustomTextTitle("테마 설정")

                CustomTextTitle("회원 설정")
            }
        }
    }
}


@Composable
fun MyPageMemberScreen(
    navController: NavController,
    onClickMenu: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp)
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
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                text = "회원 설정",
                modifier = Modifier
                    .padding(10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomText1({ onClickMenu("passwordChange") }, "비밀번호 변경")

            TextButton(onClick = { MainApplication.sharedPreferencesUtil.deleteToken() },
                modifier = Modifier
                    .fillMaxWidth()
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
                    .padding(2.dp)) {
                Text("로그아웃", fontSize = 16.sp, modifier = Modifier.padding(7.dp))
            }

            TextButton(onClick = { /* 회원 탈퇴 메서드 */ }, modifier = Modifier
                .fillMaxWidth()
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
                .padding(2.dp)) {
                Text("회원탈퇴", fontSize = 16.sp, modifier = Modifier.padding(7.dp))
            }
        }
    }


}

@Composable
fun MyPageStyleScreen(
    navController: NavController,
    onClickMenu: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp)
                .drawBehind {
                    val borderSize = 2.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = GRAY100,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize
                    )
                },
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                text = "테마 설정",
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText1({ onClickMenu("colorSetting") }, "테마색 설정")
            CustomText1({ onClickMenu("engravingSetting") }, "각인 설정")
            CustomText1({ onClickMenu("fontSetting") }, "폰트 설정")
        }
    }

}

@Composable
fun MyPagePresetScreen(
    navController: NavController,
    onClickMenu: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp)
                .drawBehind {
                    val borderSize = 2.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = GRAY100,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize
                    )
                },
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                text = "단축키 설정",
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CustomText1(onClickMenu: () -> Unit, text: String) {
    TextButton(
        onClick = onClickMenu,
        modifier = Modifier
    ) {
        Text(
            text,
            fontSize = 16.sp,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun CustomTextTitle(text: String) {
    Text(
        text,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
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
    )
}