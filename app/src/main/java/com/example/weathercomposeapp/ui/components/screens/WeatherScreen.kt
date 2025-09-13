package com.example.weathercomposeapp.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathercomposeapp.ui.components.buttons.RequestButton
import com.example.weathercomposeapp.ui.components.buttons.ResultButton
import com.example.weathercomposeapp.ui.components.cards.SearchHistoryCard
import com.example.weathercomposeapp.ui.components.cards.WeatherCard
import com.example.weathercomposeapp.ui.components.inputs.CityInput
import com.example.weathercomposeapp.ui.components.inputs.DegreesInput
import com.example.weathercomposeapp.ui.components.viewmodel.WeatherViewModel
import com.example.weathercomposeapp.ui.theme.BlackBackground
import com.example.weathercomposeapp.R

@Composable
fun WeatherScreen(){
    val viewModel = viewModel<WeatherViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(start = 10.dp, end = 10.dp),
    ) {
        Text(
            text = stringResource(R.string.input_weather_name),
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Spacer(Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy((-12).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(uiState.inputsVisible){
                Box{
                    Column {
                        CityInput(
                            city = uiState.city,
                            modifier = Modifier,
                            onValueChange = { viewModel.updateCity(it) }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        DegreesInput(
                            degrees = uiState.degrees,
                            modifier = Modifier,
                            onValueChange = { viewModel.updateDegrees(it)}
                        )
                    }
                }
            } else {
                WeatherCard(
                    city = uiState.city,
                    degrees = uiState.degrees,
                    weatherDescription = uiState.weatherDescription,
                    modifier = Modifier,
                )
            }

            Spacer(Modifier.height(48.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                if(uiState.buttonsVisible){
                    ResultButton(
                        isEnabled = uiState.isFormValid,
                        onClick = { viewModel.onBlankWeatherClick() }
                    )
                } else {
                    RequestButton(
                        onClick = { viewModel.clearAndCreateNewRequest() }
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

            SearchHistoryCard(
                searchHistory = searchHistory,
                onHistoryItemClick = { weatherData ->
                    viewModel.updateCity(weatherData.city)
                    viewModel.updateDegrees(weatherData.degrees)
                }
            )
        }
    }
}