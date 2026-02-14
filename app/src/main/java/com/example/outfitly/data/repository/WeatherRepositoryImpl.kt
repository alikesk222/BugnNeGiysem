package com.example.outfitly.data.repository

import com.example.outfitly.data.remote.api.WeatherApi
import com.example.outfitly.data.remote.dto.WeatherResponse
import com.example.outfitly.domain.model.Weather
import com.example.outfitly.domain.model.WeatherCondition
import com.example.outfitly.utils.Constants
import com.example.outfitly.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    
    override fun getWeatherByCoordinates(lat: Double, lon: Double): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        try {
            val response = weatherApi.getWeatherByCoordinates(
                lat = lat,
                lon = lon,
                apiKey = Constants.WEATHER_API_KEY
            )
            emit(Resource.Success(response.toWeather()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
    
    override fun getWeatherByCity(city: String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        try {
            val response = weatherApi.getWeatherByCity(
                city = city,
                apiKey = Constants.WEATHER_API_KEY
            )
            emit(Resource.Success(response.toWeather()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
    
    private fun WeatherResponse.toWeather(): Weather {
        val weatherMain = weather.firstOrNull()?.main ?: ""
        val weatherId = weather.firstOrNull()?.id ?: 0
        
        return Weather(
            temperature = main.temp,
            feelsLike = main.feelsLike,
            condition = mapWeatherCondition(weatherMain, weatherId),
            windSpeed = wind.speed * 3.6,
            humidity = main.humidity,
            cityName = cityName,
            isRaining = weatherId in 200..531
        )
    }
    
    private fun mapWeatherCondition(main: String, id: Int): WeatherCondition {
        return when {
            id in 200..232 -> WeatherCondition.THUNDERSTORM
            id in 300..531 -> WeatherCondition.RAIN
            id in 600..622 -> WeatherCondition.SNOW
            id in 701..781 -> WeatherCondition.MIST
            id == 800 -> WeatherCondition.CLEAR
            id in 801..804 -> WeatherCondition.CLOUDY
            else -> WeatherCondition.UNKNOWN
        }
    }
}

interface WeatherRepository {
    fun getWeatherByCoordinates(lat: Double, lon: Double): Flow<Resource<Weather>>
    fun getWeatherByCity(city: String): Flow<Resource<Weather>>
}
