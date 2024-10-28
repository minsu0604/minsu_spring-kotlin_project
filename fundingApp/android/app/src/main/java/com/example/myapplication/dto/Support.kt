package com.example.myapplication.dto

data class Support(
    val supportId:Int,
    val amount:Int,
    val user:User,
    val project: Project
)
