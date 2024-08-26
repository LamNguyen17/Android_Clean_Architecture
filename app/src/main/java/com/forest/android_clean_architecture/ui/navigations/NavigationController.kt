package com.forest.android_clean_architecture.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.forest.android_clean_architecture.ui.modules.auth.AuthScreen
import com.forest.android_clean_architecture.ui.modules.main.MainScreen
import com.forest.android_clean_architecture.ui.modules.splash.SplashScreen
import com.forest.android_clean_architecture.ui.navigations.bottom_bar.rememberAppState

@Composable
fun NavigationController() {
    val appState = rememberAppState()
    Scaffold { innerPadding ->
        NavHost(
            navController = appState.navHostController, startDestination = Root.SPLASH.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Root.SPLASH.route) { SplashScreen(appState.navHostController) }
            composable(Root.AUTH.route) { AuthScreen(appState.navHostController) }
            composable(Root.MAIN.route) { MainScreen() }
        }
    }
}