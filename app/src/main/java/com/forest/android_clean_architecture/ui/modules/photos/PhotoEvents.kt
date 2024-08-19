package com.forest.android_clean_architecture.ui.modules.photos

sealed class PhotoIntent {
    data object SearchPhotosWithoutQuery : PhotoIntent()
    data class RefreshPhotos(val q: String) : PhotoIntent()
    data class SearchPhotos(val q: String, val page: Int) : PhotoIntent()
    data class LoadMorePhotos(val q: String) : PhotoIntent()
}