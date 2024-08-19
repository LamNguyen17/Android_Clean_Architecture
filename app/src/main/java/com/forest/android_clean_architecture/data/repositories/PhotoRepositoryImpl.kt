package com.forest.android_clean_architecture.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.forest.android_clean_architecture.data.datasources.photo.PhotoRemoteDataSource
import com.forest.android_clean_architecture.data.models.toEntity
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Photos
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {
    override fun getPhoto(query: String?, page: Int): Flow<Resources<Photos>> {
        return flow {
            try {
                val response = remoteDataSource.getPhotos(query, page)
                emit(Resources.Success(data = response.toEntity()))
            } catch (e: Exception) {
                emit(Resources.Error("Failed to fetch images"))
            }
        }
    }
}