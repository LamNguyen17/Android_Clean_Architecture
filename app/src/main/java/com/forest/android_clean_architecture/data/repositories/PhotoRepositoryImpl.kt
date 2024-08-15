package com.forest.android_clean_architecture.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.forest.android_clean_architecture.data.datasources.photo.PhotoRemoteDataSource
import com.forest.android_clean_architecture.data.models.toEntity
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {
    override fun getPhoto(): Flow<Resources<List<Hits>>> {
        return flow {
            emit(Resources.Loading(isLoading = true))
            try {
                val response = remoteDataSource.getPhoto()
                emit(Resources.Success(data = response.hits.map { it.toEntity() }))
                emit(Resources.Loading(isLoading = false))
            } catch (e: Exception) {
                emit(Resources.Error("Failed to fetch images"))
                emit(Resources.Loading(isLoading = false))
            }
        }
    }
}