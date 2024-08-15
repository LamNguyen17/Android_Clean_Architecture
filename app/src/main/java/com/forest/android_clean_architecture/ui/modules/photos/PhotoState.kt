package com.forest.android_clean_architecture.ui.modules.photos

import com.forest.android_clean_architecture.domain.entities.photo.Hits

data class PhotoState(
    val hits: List<Hits> = emptyList(),
    val isLoading: Boolean = false,
    val text : String = ""
)