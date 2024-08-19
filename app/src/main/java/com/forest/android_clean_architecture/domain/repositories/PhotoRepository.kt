package com.forest.android_clean_architecture.domain.repositories

import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Photos
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhoto(query: String?, page: Int): Flow<Resources<Photos>>
}