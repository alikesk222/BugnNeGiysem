# Outfitly 👔

A modern Android weather-based outfit recommendation app built with Clean Architecture principles.

## 📱 Features

- **Real-time Weather Data**: Get current weather conditions using OpenWeatherMap API
- **Smart Outfit Recommendations**: Rule-based engine suggests outfits based on temperature, rain, and wind
- **Gender Filtering**: Filter recommendations by Male, Female, or Unisex
- **Offline Support**: Cached data available when offline
- **Location-based**: Automatic location detection or manual city entry
- **Modern UI**: Beautiful Material 3 design with Jetpack Compose

## 🏗️ Architecture

This project follows **Clean Architecture** principles with **MVVM** pattern:

```
app/
├── data/
│   ├── local/          # Room database, DAOs, Entities
│   ├── remote/         # Retrofit API, DTOs
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Business models
│   ├── usecase/        # Use cases
│   └── engine/         # Rule Engine
├── ui/
│   ├── screens/        # Compose screens
│   ├── components/     # Reusable UI components
│   ├── theme/          # Material theme
│   └── navigation/     # Navigation
├── di/                 # Hilt dependency injection
└── utils/              # Utilities and constants
```

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Local Storage**: Room Database
- **Network**: Retrofit + OkHttp
- **Async**: Coroutines + Flow
- **Location**: Google Play Services Location
- **Preferences**: DataStore

## 🎯 Key Principles

- ✅ **SOLID Principles**
- ✅ **Offline-first** approach
- ✅ **Single Source of Truth** (Room DB)
- ✅ **Unidirectional Data Flow**
- ✅ **Separation of Concerns**

## 📋 Outfit Rule Engine

The app uses a rule-based system for outfit recommendations:

| Temperature | Outfit Type |
|-------------|-------------|
| < 0°C | Heavy coat + layers |
| 0-10°C | Warm jacket + sweater |
| 10-18°C | Light jacket |
| 18-25°C | T-shirt + jeans |
| > 25°C | Light summer wear |

Additional rules:
- 🌧️ Rain detected → Waterproof items
- 💨 Wind > 25 km/h → Windbreaker
- 🌡️ Feels colder → Extra layer suggested

## 🚀 Setup

1. Clone the repository
2. Get an API key from [OpenWeatherMap](https://openweathermap.org/api)
3. Add your API key in `utils/Constants.kt`:
   ```kotlin
   const val WEATHER_API_KEY = "your_api_key_here"
   ```
4. Build and run!

## 📄 License

MIT License - feel free to use this project for learning or personal projects.

---

Built with ❤️ using Modern Android Development practices
