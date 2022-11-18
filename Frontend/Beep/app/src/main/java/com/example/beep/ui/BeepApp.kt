package com.example.beep.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.beep.R
import com.example.beep.data.BottomNavItem
import com.example.beep.di.MainApplication
import com.example.beep.ui.login.UserViewModel
import com.example.beep.ui.navigation.BeepNavGraph
import com.example.beep.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlinx.coroutines.selects.select

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BeepApp() {
    val settingFullGray = painterResource(R.drawable.settingsfull_gray500)
    val settingFullPink = painterResource(R.drawable.settingsfull_pink)
    val userGray = painterResource(R.drawable.users_gray500)
    val userPink = painterResource(R.drawable.users_pink)
    val mailGray = painterResource(R.drawable.mail_gray500)
    val mailPink = painterResource(R.drawable.mail_pink)
    val homeGray = painterResource(R.drawable.home_gray500)
    val homePink = painterResource(R.drawable.home_pink)

    val navController = rememberNavController()
    val viewModel = viewModel<UserViewModel>()
    var loginState = viewModel.loginState
    val token = MainApplication.sharedPreferencesUtil.getToken()
    LaunchedEffect(loginState.isUserLoggedIn) {
        if (token != null) {
            if (token.isEmpty()) {
                navController.navigate("login_main_graph")
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(BACKGROUND_WHITE),
        topBar = { BeepAppBar() },
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Message",
                        route = "messageList",
                        icon = mailGray,
                        selectIcon = mailPink
                    ),
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = homeGray,
                        selectIcon = homePink
                    ),
                    BottomNavItem(
                        name = "Dictionary",
                        route = "dictionary",
                        icon = settingFullGray,
                        selectIcon = settingFullPink
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = "myPage",
                        icon = settingFullGray,
                        selectIcon = settingFullPink
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                },
            )
        }
    ) {
        BeepNavGraph(navController = navController)
    }
}

@Composable
fun BeepAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
        ,
        backgroundColor = BACKGROUND_WHITE,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .width(70.dp),
                painter = painterResource(id = R.drawable.beepicon),
                contentDescription = "삡 아이콘",
                alignment =  Alignment.Center,
            )
        }
    }
}


@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    Box(
        modifier = Modifier
            .background(color = BACKGROUND_WHITE)
    ) {
        BottomNavigation(
            modifier = modifier.clip(
                RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp)
            ),
            backgroundColor = BLUE100,
            elevation = 10.dp
        ) {
            // items 배열에 담긴 모든 항목을 추가합니다.
            items.forEach { item ->
                // 뷰의 활동 상태를 백스택에 담아 저장합니다.
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (selected) {
                                Image(
                                    modifier = Modifier
                                        .size(20.dp),
                                    painter = item.selectIcon,
                                    contentDescription = item.name
                                )
                            } else {
                                Image(
                                    modifier = Modifier
                                        .size(20.dp),
                                   painter = item.icon,
                                   contentDescription = item.name
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}