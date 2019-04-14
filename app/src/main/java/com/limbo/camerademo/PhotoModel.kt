package com.limbo.camerademo


data class PhotoModel(val foods: List<Food>)
data class Food(val name: String,
                val serving: String,
                val calories: String,
                val totalfat: String,
                val transfat: String,
                val cholestrol: String,
                val sodium: String,
                val totalcarbs: String,
                val dietaryfiber: String,
                val sugar: String,
                val protein: String,
                val vitaminA: String,
                val vitaminC: String,
                val calcium: String,
                val iron: String)