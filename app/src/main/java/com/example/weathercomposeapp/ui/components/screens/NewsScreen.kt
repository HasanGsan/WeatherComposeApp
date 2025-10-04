package com.example.weathercomposeapp.ui.components.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathercomposeapp.ui.theme.BlackBackground
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.components.cards.recycler.cards.newsScreen.NewsCard
import com.example.weathercomposeapp.ui.components.cards.recycler.cards.newsScreen.PreviewNewsCard
import com.example.weathercomposeapp.ui.components.data.local.DatabaseProvider
import com.example.weathercomposeapp.ui.components.data.local.factory.NewsViewModelFactory
import com.example.weathercomposeapp.ui.components.data.repository.RoomNewsRepository
import com.example.weathercomposeapp.ui.components.viewmodel.NewsViewModel
import com.example.weathercomposeapp.ui.theme.WhiteColor

@Preview
@Composable
fun NewsScreen(
    context: Context = LocalContext.current
) {
    val db = DatabaseProvider.getDatabase(context)
    val repository = RoomNewsRepository(db.newsStatusDao())
    val viewModel: NewsViewModel = viewModel(
        factory = NewsViewModelFactory(repository)
    )

    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    val openNewsId = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.loadNews() }

    LaunchedEffect(Unit) { viewModel.refreshFavorites() }

    Box(
        modifier = Modifier.fillMaxSize().background(BlackBackground)
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ошибка ${uiState.errorMessage}")
                }
            }
            else -> {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.screen_news_title),
                            color = WhiteColor,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            fontSize = 20.sp
                        )
                    }

                    items(
                        count = uiState.newsItems.size,
                        key = { index -> uiState.newsItems[index].newsData.id }
                    ) { index ->
                        val newsItems = uiState.newsItems[index]
                        NewsCard(
                            newsItem = newsItems,
                            onToggleRead = { id -> viewModel.toggleRead(id)
                            },
                            onOpen = { id -> openNewsId.value = id },
                            onToggleFavorite = { id -> viewModel.toggleFavorite(id) }
                        )
                    }
                }
                openNewsId.value?.let { newsId ->
                    val newsItem = uiState.newsItems.find { it.newsData.id == newsId } ?: return@let

                    Dialog(
                        onDismissRequest = { openNewsId.value = null },
                        properties = DialogProperties(
                            usePlatformDefaultWidth = false
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.6f)),
                            contentAlignment = Alignment.Center
                        ) {
                            PreviewNewsCard(
                                newsItem = newsItem,
                                onToggleRead = {  id -> viewModel.toggleRead(id) },
                                onClose = { openNewsId.value = null },
                                onToggleFavorite = { id -> viewModel.toggleFavorite(id) }
                            )
                        }
                    }

                }
            }
        }

    }
}
