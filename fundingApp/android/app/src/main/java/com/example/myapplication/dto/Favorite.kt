package com.example.myapplication.dto



data class Favorite(
    val favoriteId: Int,
    val user:User,
    val project:Project
)
