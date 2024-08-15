package com.forest.android_clean_architecture.ui.modules.photos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.forest.android_clean_architecture.R
import com.forest.android_clean_architecture.common.Resources
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(viewModel: PhotoViewModel = hiltViewModel()) {
    val state = viewModel.posts.collectAsState()
    val listState = rememberLazyListState() // Remember the scroll state

    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = { Text("Android Clean Architecture") })
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (state.value) {
                    is Resources.Loading -> CircularProgressIndicator()
                    is Resources.Success -> {
                        val hits = (state.value as Resources.Success<List<Hits>>).data
                        if (hits.isNullOrEmpty()) {
                            Text(text = "No photo found")
                        } else {
                            SwipeRefresh(state = SwipeRefreshState(isRefreshing = false),
                                onRefresh = { viewModel.onIntent(PhotoIntent.FetchPhoto) }) {
                                LazyColumn(
                                    state = listState,  // Use the remembered scroll state
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(items = hits, key = { it.id }) {
                                        PhotoRow(it)
                                    }
                                }
                            }
                        }
                    }

                    is Resources.Error -> Text(text = "Error")

//                    state.hits.isEmpty() -> Text(text = "No photo found")
//                    state.hits.isNotEmpty() -> {
//                        SwipeRefresh(state = SwipeRefreshState(isRefreshing = false),
//                            onRefresh = { viewModel.onEvent(PhotoEvents.FetchPhoto) }) {
//                            LazyColumn(
//                                state = listState,  // Use the remembered scroll state
//                                verticalArrangement = Arrangement.spacedBy(4.dp)
//                            ) {
//                                items(items = state.hits, key = { it.id }) {
//                                    PhotoRow(it)
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    })
}

@Composable
fun PhotoRow(hit: Hits) {
    Card(
        shape = RoundedCornerShape(8.dp), // Set the border radius here
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(
                start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp
            ) // Set left and right margins
            .fillMaxWidth()
            .clickable {},
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(hit.previewURL)
                    .crossfade(true) // Optional crossfade animation
                    .placeholder(R.drawable.placeholder_image) // Default image while loading
                    .error(R.drawable.placeholder_image) // Image if there's an error
                    .size(Size.ORIGINAL) // Optionally specify size to preload at
                    .memoryCachePolicy(CachePolicy.ENABLED) // Enable memory caching
                    .build(),
                contentDescription = "Preview image of ${hit.user}",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = hit.user, fontWeight = FontWeight.Bold)
                Text(text = "Thẻ: ${hit.tags}", maxLines = 1)
                Text(text = "Lượt thích: ${hit.likes}")
                Text(text = "Bình luận: ${hit.comments}")
            }
        }
    }
}