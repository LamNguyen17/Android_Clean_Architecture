package com.forest.android_clean_architecture.ui.modules.photos

import com.forest.android_clean_architecture.domain.entities.photo.Hits

sealed class PhotoEvents {
    data class PhotoLoaded(val hits: List<Hits>) : PhotoEvents()
    data class UpdateText(val q: String) : PhotoEvents()
}