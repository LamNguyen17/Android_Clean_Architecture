package com.forest.android_clean_architecture.ui.modules.photos

sealed class PhotoIntent {
    data object FetchPhoto : PhotoIntent()
//    data object RefreshPhotos : PhotoIntent()
    data class SearchPhotos(val q: String, val page: Int) : PhotoIntent()
//    data class LoadMorePhotos(val q: String, val page: Int) : PhotoIntent()
}