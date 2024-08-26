package com.forest.android_clean_architecture.ui.modules.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forest.android_clean_architecture.ui.modules.main.home.HomeScreen
import com.forest.android_clean_architecture.ui.modules.main.profile.ProfileScreen
import com.forest.android_clean_architecture.ui.navigations.bottom_bar.BottomBarRoutes
import com.forest.android_clean_architecture.ui.navigations.bottom_bar.BottomBarRow

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBarRow(navHostController) },
    ) { paddingValues ->
        NavHost(
            navHostController, startDestination = BottomBarRoutes.HOME.routes,
            modifier = Modifier.padding(paddingValues),
//        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None },
//        popEnterTransition = { EnterTransition.None },
//        popExitTransition = { ExitTransition.None },
        ) {
            composable(BottomBarRoutes.HOME.routes) {
                HomeScreen(navHostController = navHostController)
            }
            composable(BottomBarRoutes.Profile.routes) {
                ProfileScreen(navHostController = navHostController)
            }
            // Add other screens as needed
        }
    }
}