package com.example.weathercomposeapp.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathercomposeapp.ui.theme.BlackBackground
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.components.cards.recycler.cards.favoriteScreen.FavoriteCard
import com.example.weathercomposeapp.ui.components.viewmodel.FavoriteViewModel
import com.example.weathercomposeapp.ui.theme.BoxColor
import com.example.weathercomposeapp.ui.theme.TagPostColor

@Preview
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = viewModel()){
    val uiState = viewModel.uiState.collectAsState().value
    val selectedTag = viewModel.selectedTag.collectAsState().value
    val tags = viewModel.allTags.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(BlackBackground).padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.screen_favorite_title),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                val isSelected = tag == selectedTag
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = BoxColor,
                            shape = RoundedCornerShape(3.dp)
                        )
                ) {
                    Text(
                        text = tag,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .background(
                                color = if (isSelected) TagPostColor else Color.Transparent,
                                shape = RoundedCornerShape(3.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickable { viewModel.selectTag(tag) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.newsItems) { newsItem ->
                FavoriteCard(
                    newsItem = newsItem,
                    onToggleFavorite = { id ->
                        viewModel.toggleFavorite(id)
                    },
                )
            }
        }
    }
}