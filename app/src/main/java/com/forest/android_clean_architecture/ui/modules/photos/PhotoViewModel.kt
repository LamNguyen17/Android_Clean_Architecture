package com.forest.android_clean_architecture.ui.modules.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.forest.android_clean_architecture.domain.usecases.photos.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }
    private val loadMoreTrigger = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    private var currentPage = 1
    private val appendPhotos = mutableListOf<Hits>()
//    private val appendPhotos = mutableListOf<List<Hits>>(emptyList())

    val state: StateFlow<SearchState?> = combine(
        queryFlow,
        refreshTrigger,
        loadMoreTrigger,
    ) { query, _, _ -> query }
        .debounce(350)
        .flatMapLatest {
            getPhotoUseCase.invoke(it, currentPage).onEach { resource ->
                if (resource is Resources.Success) {
                    if (currentPage == 1) {
                        appendPhotos.clear()
                    }
                    val hits = resource.data as List<Hits>
                    appendPhotos.addAll(hits)
                }
            }
        }
        .map { resource ->
            when (resource) {
                is Resources.Loading -> SearchState.Loading
                is Resources.Success -> SearchState.Success(appendPhotos.toList())
                is Resources.Error -> SearchState.Error(resource.message.toString())
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchState.Idle)

    fun onIntent(event: PhotoIntent) {
        when (event) {
            is PhotoIntent.SearchPhotosWithoutQuery -> queryFlow.value = ""
            is PhotoIntent.SearchPhotos -> {
                currentPage = 1
                queryFlow.value = event.q
            }
            is PhotoIntent.RefreshPhotos -> {
                currentPage = 1
                refreshTrigger.tryEmit(Unit)
            }
            is PhotoIntent.LoadMorePhotos -> {
                currentPage += 1
                loadMoreTrigger.tryEmit(Unit)
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