package com.example.myapplication.retrofitPacket

data class ProjectWrite(
    val projectId: Int,
    val goalAmount: Int,
    val currentAmount: Int, // 기본값
    val title: String,
    val contents: String,
    val startDate: String,
    val endDate: String,
    val perPrice: Int,
    val thumbnail: String,
    val user:UserPacket,
    val category: CategoryPacket
)
