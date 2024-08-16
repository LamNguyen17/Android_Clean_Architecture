package com.forest.android_clean_architecture.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.forest.android_clean_architecture.data.datasources.photo.PhotoRemoteDataSource
import com.forest.android_clean_architecture.data.models.toEntity
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.time.debounce

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {
    override fun getPhoto(query: String?): Flow<Resources<List<Hits>>> {
        return flow {
            try {
                println("PhotoRepositoryImpl: $query")
                if (query.isNullOrEmpty()) {
                    val response = remoteDataSource.getPhotosWithoutQuery()
                    emit(Resources.Success(data = response.hits.map { it.toEntity() }))
                } else {
                    val response = remoteDataSource.getPhotos(query)
                    emit(Resources.Success(data = response.hits.map { it.toEntity() }))
                }
            } catch (e: Exception) {
                emit(Resources.Error("Failed to fetch images"))
            }
        }
    }
}