package com.forest.android_clean_architecture.ui.modules.photos

sealed class PhotoIntent {
    data object FetchPhoto : PhotoIntent()
}