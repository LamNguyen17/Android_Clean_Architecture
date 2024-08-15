package com.forest.android_clean_architecture.ui.modules.photos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.usecases.photos.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) :
    ViewModel() {
    private val _posts = MutableStateFlow<Resources<List<Hits>>>(Resources.Loading())
    val posts: StateFlow<Resources<List<Hits>>> = _posts

    var state by mutableStateOf(PhotoState())
        private set

    fun onEvent(event: PhotoEvents) {
        when (event) {
            is PhotoEvents.PhotoLoaded -> {
                fetchPosts()
            }

            is PhotoEvents.UpdateText -> {
            }
        }
    }

    init {
//        fetchPosts()
        onEvent(PhotoEvents.PhotoLoaded(emptyList()))
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            getPhotoUseCase().collect { resource ->
                print("viewModelScope: ${resource.data}")
                _posts.value = resource
                state = when (resource) {
                    is Resources.Loading -> state.copy(isLoading = resource.isLoading)
                    is Resources.Error -> state.copy(isLoading = false)
                    is Resources.Success -> state.copy(
                        hits = resource.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }
}