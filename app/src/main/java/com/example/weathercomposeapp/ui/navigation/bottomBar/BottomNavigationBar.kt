package com.example.weathercomposeapp.ui.navigation.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercomposeapp.ui.components.data.navigationData.BottomNavItem
import com.example.weathercomposeapp.ui.navigation.mainComponents.RootComponents
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.navigation.screens.Screen
import com.example.weathercomposeapp.ui.theme.BlackBackground
import com.example.weathercomposeapp.ui.theme.WhiteColor

@Composable
fun BottomNavigationBar(rootComponents: RootComponents) {

    val currentScreen by rootComponents.currentScreen.collectAsState()

    val items = listOf(
        BottomNavItem(
            title = "Погода",
            image = painterResource(R.drawable.ic_weather),
            route = Screen.WEATHER
        ),
        BottomNavItem(
            title = "Новости",
            image = painterResource(R.drawable.ic_new),
            route = Screen.NEWS
        ),
        BottomNavItem(
            title = "Избранное",
            image = painterResource(R.drawable.ic_favorite_white),
            route = Screen.FAVORITE
        )
    )

    NavigationBar(containerColor = BlackBackground){
        items.forEach { item ->
            val isSelected = currentScreen == item.route
            NavigationBarItem(
                icon = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(4.dp)
                                .background(
                                    color = if (isSelected) WhiteColor.copy(alpha = 0.2f) else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.5f) else Color.White.copy(
                                        alpha = 0.2f
                                    ),
                                    shape = RoundedCornerShape(6.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = item.image,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f)
                                )

                                Text(
                                    text = item.title,
                                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                },
                selected = isSelected,
                onClick = {
                    when (item.route){
                        Screen.WEATHER -> rootComponents.showWeather()
                        Screen.NEWS -> rootComponents.showNews()
                        Screen.FAVORITE -> rootComponents.showFavorite()
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
