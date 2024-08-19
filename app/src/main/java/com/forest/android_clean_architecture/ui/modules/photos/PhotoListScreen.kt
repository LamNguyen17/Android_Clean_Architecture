package com.forest.android_clean_architecture.ui.modules.photos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size

import com.forest.android_clean_architecture.R
import com.forest.android_clean_architecture.domain.entities.photo.Hits
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(viewModel: PhotoViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()
    val listState = rememberLazyListState() // Remember the scroll state

    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = { Text("Android Clean Architecture") })
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally, // Horizontally centers children
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.onIntent(PhotoIntent.SearchPhotos(it, 1))
                },
                label = { Text("Enter text") },
                placeholder = { Text("Type something...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
            )

            Spacer(modifier = Modifier.height(0.dp))

            when (state.value) {
                is SearchState.Loading -> CircularProgressIndicator()
                is SearchState.Success -> {
                    val hits = (state.value as SearchState.Success).data.hits
                    println("PhotoListScreen_value: ${(state.value as SearchState.Success).data.totalHits} - ${hits.size}")
                    if (hits.isNullOrEmpty()) {
                        Text(text = "No photo found")
                    } else {
                        focusManager.clearFocus()
                        SwipeRefresh(state = SwipeRefreshState(isRefreshing = false),
                            onRefresh = { viewModel.onIntent(PhotoIntent.RefreshPhotos(query)) }) {
                            LazyColumn(
                                state = listState,  // Use the remembered scroll state
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(items = hits, key = { it.id }) {
                                    PhotoRow(it)
                                }
                                if (hits.size < (state.value as SearchState.Success).data.totalHits) {
                                    item {
                                        LaunchedEffect(Unit) {
                                            viewModel.onIntent(PhotoIntent.LoadMorePhotos(query))
                                        }
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is SearchState.Error -> Text(text = "Error")
                is SearchState.Idle -> Text(text = "Idle")
                else -> Text(text = "null")
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