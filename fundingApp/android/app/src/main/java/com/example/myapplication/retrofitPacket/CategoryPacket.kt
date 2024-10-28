package com.example.myapplication.retrofitPacket

import java.io.Serializable

data class CategoryPacket(
    val categoryId:Int,
    val title:String
): Serializable
