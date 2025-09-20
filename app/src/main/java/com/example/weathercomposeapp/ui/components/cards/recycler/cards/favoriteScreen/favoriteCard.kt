package com.example.weathercomposeapp.ui.components.cards.recycler.cards.favoriteScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState
import com.example.weathercomposeapp.ui.theme.BoxColor

@Composable
fun FavoriteCard(
    newsItem: NewsItemUiState,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isRemoving by remember { mutableStateOf(false) }
    var showFilledHeart by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isRemoving) 0f else 1f,
        animationSpec = tween(300)
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .border(
                width = 1.dp,
                color = BoxColor,
                shape = RoundedCornerShape(6.dp)
            )
            .alpha(alpha)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Image(
                    painter = painterResource(newsItem.newsData.preview),
                    contentDescription = stringResource(R.string.input_degrees_name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .width(64.dp),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = newsItem.newsData.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = BoxColor,
                            shape = RoundedCornerShape(3.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 6.dp)
                ) {

                    Text(
                        text = newsItem.newsData.tags,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

            }

            Spacer(modifier = Modifier.width(32.dp))

            IconButton(
                onClick = {
                    showFilledHeart = true
                    isRemoving = true
                    onToggleFavorite(newsItem.newsData.id)
                },
                modifier = Modifier
                    .size(28.dp),
            ) {
                Icon(
                    painter = painterResource(id = if (showFilledHeart) R.drawable.ic_favorite_border else R.drawable.ic_favorite_white),
                    tint = Color.White,
                    contentDescription = "Добавить в избранное",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}