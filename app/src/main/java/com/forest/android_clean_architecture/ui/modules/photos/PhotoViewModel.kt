package com.forest.android_clean_architecture.ui.modules.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.usecases.photos.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) :
    ViewModel() {
    private val _posts = MutableStateFlow<Resources<List<Hits>>>(Resources.Loading())
    val posts: StateFlow<Resources<List<Hits>>> = _posts

    fun onIntent(event: PhotoIntent) {
        when (event) {
            is PhotoIntent.FetchPhoto -> {
                fetchPosts()
            }
        }
    }

    init {
        onIntent(PhotoIntent.FetchPhoto)
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = Resources.Loading()
            getPhotoUseCase.invoke()
                .catch { e -> _posts.value = Resources.Error(e.message ?: "Unknown Error") }
                .collect { resource ->
                    _posts.value = Resources.Success(resource.data)
                }
        }
    }
}