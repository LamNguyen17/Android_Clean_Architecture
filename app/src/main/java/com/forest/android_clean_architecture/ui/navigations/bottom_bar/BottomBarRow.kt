package com.forest.android_clean_architecture.ui.navigations.bottom_bar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.forest.android_clean_architecture.R
import com.forest.android_clean_architecture.ui.navigations.Main

@Composable
fun BottomBarRow(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Trang chủ", R.drawable.bottom_tab_home, Main.HOME.route),
        BottomNavItem("Tài khoản", R.drawable.bottom_tab_profile, Main.PROFILE.route)
    )

    NavigationBar(modifier = Modifier.height(80.dp)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    val bool = currentRoute(navController) == item.route
                    val tintColor =
                        if (bool) Color("#67A346".toColorInt()) else Color("#7F7D83".toColorInt())
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            tint = tintColor,
                            modifier = Modifier.height(24.dp)
                        )

                    }
                },
                label = {
                    val bool = currentRoute(navController) == item.route
                    val tintColor =
                        if (bool) Color("#67A346".toColorInt()) else Color("#7F7D83".toColorInt())
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            color = tintColor
                        )
                    )
                },
                selected = currentRoute(navController) == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, @DrawableRes val icon: Int, val route: String)

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

enum class BottomBarRoutes(
    val id: Int,
    val title: String,
    val routes: String,
    @DrawableRes val icon: Int
) {
    HOME(1, "Trang chủ", Main.HOME.route, R.drawable.bottom_tab_home),
    Profile(2, "Tài khoản", Main.PROFILE.route, R.drawable.bottom_tab_profile),
}