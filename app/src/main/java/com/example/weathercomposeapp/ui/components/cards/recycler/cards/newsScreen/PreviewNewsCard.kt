package com.example.weathercomposeapp.ui.components.cards.recycler.cards.newsScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState
import com.example.weathercomposeapp.ui.theme.BoxColor
import com.example.weathercomposeapp.ui.theme.NotReadBg
import com.example.weathercomposeapp.ui.theme.ReadBg
import com.example.weathercomposeapp.ui.theme.TagPostColor
import com.example.weathercomposeapp.ui.theme.WhiteColor

@Composable
fun PreviewNewsCard(
    newsItem: NewsItemUiState,
    onToggleRead: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    val containerColor by animateColorAsState(
        targetValue = if(newsItem.isRead) ReadBg else NotReadBg
    )

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.Transparent)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .size(32.dp)
                    .background(Color.White)
                    .clickable { onClose() },
                ) {
                Text(
                    text = "×",
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = containerColor,),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp).fillMaxWidth(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            fontSize = 16.sp
                        )
                    }
                    Box {
                        Row(
                            modifier = Modifier.padding(end = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_access_time),
                                contentDescription = stringResource(R.string.input_degrees_name),
                                tint = Color.White,
                                modifier = Modifier.size(22.dp).padding(end = 5.dp)
                            )
                            Text(
                                text = "${newsItem.newsData.timeCreated} минут назад",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = BoxColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = newsItem.newsData.postCategory, color = TagPostColor, fontSize = 16.sp)
                    Text(text = "Время чтения: 10 минут", color = TagPostColor, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Column {
                    Image(
                        painter = painterResource(R.drawable.ic_barcelona_icon),
                        contentDescription = "Фото",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                            .height(120.dp)
                    )

                    Text(
                        text = newsItem.newsData.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = newsItem.newsData.content,
                        color = Color.White,
                        lineHeight = 24.sp,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = BoxColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    IconButton(
                        onClick = { onToggleRead(newsItem.newsData.id) },
                        modifier = Modifier
                            .background(color = WhiteColor, shape = RoundedCornerShape(3.dp))
                            .width(32.dp)
                            .height(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_post),
                            contentDescription = "Просмотреть",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    IconButton(
                        onClick = { onToggleFavorite(newsItem.newsData.id) },
                        modifier = Modifier
                            .background(color = WhiteColor, shape = RoundedCornerShape(3.dp))
                            .width(32.dp)
                            .height(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (newsItem.isFavorite) R.drawable.ic_favorite_black else R.drawable.ic_favorite_border
                            ),
                            contentDescription = "Добавить в избранное",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
