package com.example.beep.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.beep.data.BottomNavItem

@Composable
fun BeepApp() {
    val navController = rememberNavController()
    Scaffold(topBar = { BeepAppBar() },
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Chat",
                        route = "chat",
                        icon = Icons.Default.Notifications,
                        badgeCount = 24
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = "settings",
                        icon = Icons.Default.Settings
                    ),
                    BottomNavItem(
                        name = "LoginMain",
                        route = "login_main",
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
    TopAppBar(modifier = modifier.fillMaxWidth()) {
        Text(text = "Beep", modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        // items 배열에 담긴 모든 항목을 추가합니다.
        items.forEach { item ->
            // 뷰의 활동 상태를 백스택에 담아 저장합니다.
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Green,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        // 아이콘이 선택 되었을 때, 아이콘 밑에 텍스트를 표시합니다.
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}