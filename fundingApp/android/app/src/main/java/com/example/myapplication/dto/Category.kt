package com.example.myapplication.dto

import java.io.Serializable

data class Category(
    val categoryId: Int,
    val title: String,
    val projectList: List<Project>
)