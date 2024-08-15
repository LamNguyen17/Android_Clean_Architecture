package com.forest.android_clean_architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.forest.android_clean_architecture.ui.modules.photos.PhotoListScreen
import com.forest.android_clean_architecture.ui.modules.photos.PhotoViewModel
import com.forest.android_clean_architecture.ui.theme.Android_Clean_ArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_Clean_ArchitectureTheme {
                val photosViewModel: PhotoViewModel = hiltViewModel()
                PhotoListScreen(photosViewModel)
            }
        }
    }
}