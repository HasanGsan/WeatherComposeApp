package com.example.weathercomposeapp.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercomposeapp.ui.theme.BlackGrayBackground
import com.example.weathercomposeapp.ui.theme.BoxColor
import com.example.weathercomposeapp.R

@Composable
fun WeatherCard(
    city: String,
    degrees: String,
    modifier: Modifier,
    weatherDescription: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BlackGrayBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ){
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
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = city.uppercase(),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Row(
                modifier = Modifier
                    .padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_union),
                    contentDescription = stringResource(R.string.input_degrees_name),
                    tint = Color.White,
                    modifier = Modifier.size(16.dp),
                    )

                Text(
                    text = degrees,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = BoxColor,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = weatherDescription,
            color = Color.White,
            fontSize = 16.sp
        )

    }


}