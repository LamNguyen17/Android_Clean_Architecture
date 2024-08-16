package com.forest.android_clean_architecture.domain.repositories

import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.common.Resources
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhoto(query: String?): Flow<Resources<List<Hits>>>
}