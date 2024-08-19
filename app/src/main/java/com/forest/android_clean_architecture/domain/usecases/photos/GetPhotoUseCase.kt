package com.forest.android_clean_architecture.domain.usecases.photos

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository
import kotlinx.coroutines.flow.flow

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(query: String?, page: Int): Flow<Resources<List<Hits>>> {
        return if (query.isNullOrEmpty()) {
            flow {
                emit(Resources.Success(data = emptyList()))
            }
        } else {
            repository.getPhoto(query, page)
        }
    }
}