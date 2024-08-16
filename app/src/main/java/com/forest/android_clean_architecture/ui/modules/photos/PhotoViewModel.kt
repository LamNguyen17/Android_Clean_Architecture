package com.forest.android_clean_architecture.ui.modules.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.usecases.photos.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import javax.inject.Inject

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val data: List<Hits>) : SearchState()
    data class Error(val message: String) : SearchState()
}

@HiltViewModel
class PhotoViewModel @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) :
    ViewModel() {
    private val queryFlow = MutableStateFlow("")
    private val _state = MutableStateFlow<SearchState>(SearchState.Idle)

    val state: StateFlow<SearchState?> = queryFlow
        .debounce(350) // Adding debounce to prevent making requests on every keystroke
        .distinctUntilChanged() // Optional: Skip empty queries
        .flatMapLatest {
            if (it.isEmpty()) {
                getPhotoUseCase.invoke(null)
            } else {
                getPhotoUseCase.invoke(it)
            }
        }
        .map { resource ->
            when (resource) {
                is Resources.Loading -> SearchState.Loading
                is Resources.Success -> resource.data?.let { SearchState.Success(it) }
                is Resources.Error -> resource.message?.let { SearchState.Error(it) }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchState.Idle)

    fun onIntent(event: PhotoIntent) {
        when (event) {
            is PhotoIntent.SearchPhotosWithoutQuery -> {
                queryFlow.value = ""
            }
            is PhotoIntent.SearchPhotos -> {
                queryFlow.value = event.q
            }
        }
    }

    init {
        onIntent(PhotoIntent.SearchPhotosWithoutQuery)
    }

    private fun fetchPosts() {
//        viewModelScope.launch {
//            _posts.value = Resources.Loading()
//            getPhotoUseCase.invoke(null)
//                .catch { e -> _posts.value = Resources.Error(e.message ?: "Unknown Error") }
//                .collect { resource ->
//                    _posts.value = Resources.Success(resource.data)
//                }
//        }
    }
}