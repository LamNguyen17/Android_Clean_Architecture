package com.forest.android_clean_architecture.ui.modules.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.forest.android_clean_architecture.ui.modules.main.home.HomeScreen
import com.forest.android_clean_architecture.ui.modules.main.profile.ProfileScreen
import com.forest.android_clean_architecture.ui.navigations.Root
import com.forest.android_clean_architecture.ui.navigations.bottom_bar.BottomBarRoutes
import com.forest.android_clean_architecture.ui.navigations.bottom_bar.BottomBarRow

@Composable
fun AuthScreen(
    navHostController: NavHostController
) {
    Button(onClick = {
        navHostController.navigate(Root.MAIN.route) {
            popUpTo(Root.AUTH.route) { inclusive = true }
        }
    }) {
        Text("Login")
    }
//    Scaffold(
//        bottomBar = { BottomBarRow(navHostController) },
//        floatingActionButtonPosition = FabPosition.Center,
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            NavHost(
//                navHostController, startDestination = BottomBarRoutes.HOME.routes,
//                modifier = Modifier.padding(paddingValues),
////                enterTransition = { EnterTransition.None },
////                exitTransition = { ExitTransition.None },
////                popEnterTransition = { EnterTransition.None },
////                popExitTransition = { ExitTransition.None },
//            ) {
//                composable(BottomBarRoutes.HOME.routes) {
//                    HomeScreen(navHostController = navHostController)
//                }
//                composable(BottomBarRoutes.Profile.routes) {
//                    ProfileScreen(navHostController = navHostController)
//                }
//                // Add other screens as needed
//            }
//        }
//    }
}