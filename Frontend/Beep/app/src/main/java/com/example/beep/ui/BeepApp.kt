package com.example.beep.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.beep.ui.theme.BACKGROUND_WHITE
import com.example.beep.ui.theme.GRAY100
import com.example.beep.ui.theme.GRAY500
import com.example.beep.ui.theme.PINK500

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BeepApp() {
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
            .imePadding()
            .padding(0.dp,25.dp)
        ,
        topBar = { BeepAppBar() },
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Message",
                        route = "message",
                        icon = Icons.Outlined.Message,
                        badgeCount = 24
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = "settings",
                        icon = Icons.Outlined.Settings
                    ),
                    BottomNavItem(
                        name = "LoginMain",
                        route = "login_main",
                        icon = Icons.Default.Person
                    ),
                    BottomNavItem(
                        name = "SavedMessage",
                        route = "savedMessage",
                        icon = Icons.Default.Person
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                })
        }
    ) {
        BeepNavGraph(navController = navController)
    }
}

@Composable
fun BeepAppBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier.fillMaxWidth().height(80.dp), backgroundColor = BACKGROUND_WHITE) {
        Text(text = "BEEP", modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center, color= PINK500,
            fontFamily = galmurinineFont, fontSize = 25.sp
        )
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
    BottomNavigation(
        modifier = modifier,
        backgroundColor = GRAY100,
        elevation = 10.dp
    ) {
        // items 배열에 담긴 모든 항목을 추가합니다.
        items.forEach { item ->
            // 뷰의 활동 상태를 백스택에 담아 저장합니다.
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = PINK500,
                unselectedContentColor = GRAY500,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        // 아이콘이 선택 되었을 때, 아이콘 밑에 텍스트를 표시합니다.
//                        if (selected) {
//                            Text(
//                                text = item.name,
//                                textAlign = TextAlign.Center,
//                                fontSize = 10.sp
//                            )
//                        }
                    }
                }
            )
        }
    }
}