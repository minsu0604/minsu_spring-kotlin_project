package com.example.myapplication.dto

import java.io.Serializable
import java.time.LocalDateTime


data class Project(
    val projectId: Int,
    val goalAmount:Int,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate:String,
    val perPrice:Int,
    val thumbnail:String,
    val user:User,
    val supportList:List<Support>,
    val favoriteList:List<Favorite>,
    val category: Category
): Serializable
