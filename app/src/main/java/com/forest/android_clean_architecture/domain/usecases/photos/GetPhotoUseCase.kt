package com.forest.android_clean_architecture.domain.usecases.photos

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(query: String?): Flow<Resources<List<Hits>>> {
        return repository.getPhoto(query)
    }
}