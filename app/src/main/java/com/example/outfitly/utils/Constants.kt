package com.example.outfitly.utils

object Constants {
    const val WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val WEATHER_API_KEY = "cc6cb89ec6d7d008b5908a13b36e351d"
    
    const val DATABASE_NAME = "outfitly_database"
    
    const val WIND_THRESHOLD_KMH = 25.0
    
    object PreferencesKeys {
        const val GENDER = "gender"
        const val LAST_CITY = "last_city"
        const val IS_PREMIUM = "is_premium"
    }
}
