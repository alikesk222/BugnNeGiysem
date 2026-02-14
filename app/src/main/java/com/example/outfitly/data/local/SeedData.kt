package com.example.outfitly.data.local

import com.example.outfitly.data.local.entity.OutfitEntity

object SeedData {
    
    val outfits = listOf(
        // Very Cold (< 0°C)
        OutfitEntity(
            title = "Winter Warrior",
            description = "Stay warm in freezing temperatures",
            items = "Thick Down Coat,Wool Sweater,Thermal Underwear,Winter Boots,Beanie,Gloves,Scarf",
            gender = "MALE",
            minTemp = -30,
            maxTemp = 0,
            rainCompatible = true,
            windCompatible = true
        ),
        OutfitEntity(
            title = "Snow Queen",
            description = "Elegant winter protection",
            items = "Long Puffer Coat,Cashmere Sweater,Fleece Leggings,Fur Boots,Wool Hat,Leather Gloves",
            gender = "FEMALE",
            minTemp = -30,
            maxTemp = 0,
            rainCompatible = true,
            windCompatible = true
        ),
        
        // Cold (0-10°C)
        OutfitEntity(
            title = "Urban Explorer",
            description = "Perfect for chilly days",
            items = "Wool Coat,Hoodie,Jeans,Leather Boots,Beanie",
            gender = "MALE",
            minTemp = 0,
            maxTemp = 10,
            rainCompatible = false,
            windCompatible = true
        ),
        OutfitEntity(
            title = "Cozy Chic",
            description = "Stylish and warm",
            items = "Trench Coat,Turtleneck Sweater,Slim Jeans,Ankle Boots,Scarf",
            gender = "FEMALE",
            minTemp = 0,
            maxTemp = 10,
            rainCompatible = false,
            windCompatible = true
        ),
        OutfitEntity(
            title = "Rainy Day Ready",
            description = "Waterproof essentials",
            items = "Waterproof Jacket,Sweater,Dark Jeans,Rain Boots",
            gender = "UNISEX",
            minTemp = 0,
            maxTemp = 10,
            rainCompatible = true,
            windCompatible = true
        ),
        
        // Cool (10-18°C)
        OutfitEntity(
            title = "Casual Cool",
            description = "Light layering for mild weather",
            items = "Denim Jacket,T-Shirt,Chinos,Sneakers",
            gender = "MALE",
            minTemp = 10,
            maxTemp = 18,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Spring Vibes",
            description = "Fresh and trendy",
            items = "Light Cardigan,Blouse,High-Waist Jeans,Loafers",
            gender = "FEMALE",
            minTemp = 10,
            maxTemp = 18,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Windbreaker Set",
            description = "Protection from cool winds",
            items = "Windbreaker Jacket,Long Sleeve Tee,Joggers,Running Shoes",
            gender = "UNISEX",
            minTemp = 10,
            maxTemp = 18,
            rainCompatible = true,
            windCompatible = true
        ),
        
        // Warm (18-25°C)
        OutfitEntity(
            title = "Street Style",
            description = "Comfortable and stylish",
            items = "Graphic Tee,Slim Fit Jeans,White Sneakers,Cap",
            gender = "MALE",
            minTemp = 18,
            maxTemp = 25,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Effortless Elegance",
            description = "Light and breezy",
            items = "Flowy Dress,Sandals,Crossbody Bag,Sunglasses",
            gender = "FEMALE",
            minTemp = 18,
            maxTemp = 25,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Polo Classic",
            description = "Smart casual comfort",
            items = "Polo Shirt,Khaki Shorts,Boat Shoes",
            gender = "MALE",
            minTemp = 18,
            maxTemp = 25,
            rainCompatible = false,
            windCompatible = false
        ),
        
        // Hot (25°C+)
        OutfitEntity(
            title = "Beach Ready",
            description = "Stay cool in the heat",
            items = "Linen Shirt,Swim Shorts,Flip Flops,Sunglasses",
            gender = "MALE",
            minTemp = 25,
            maxTemp = 50,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Summer Breeze",
            description = "Light and airy",
            items = "Crop Top,Flowy Skirt,Sandals,Sun Hat",
            gender = "FEMALE",
            minTemp = 25,
            maxTemp = 50,
            rainCompatible = false,
            windCompatible = false
        ),
        OutfitEntity(
            title = "Minimal Heat",
            description = "Maximum comfort in hot weather",
            items = "Tank Top,Cotton Shorts,Slides,Cap",
            gender = "UNISEX",
            minTemp = 25,
            maxTemp = 50,
            rainCompatible = false,
            windCompatible = false
        )
    )
}
