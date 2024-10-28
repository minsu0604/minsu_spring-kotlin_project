package com.example.myapplication.retrofitPacket

data class HomeInitPacket(
    val bannerUrl:List<String>,
    val categorys: List<CategoryPacket>,
    val popularProjects:List<ProjectDetail>,
    val deadlineProjects:List<ProjectDetail>,
    val scrollProjects:List<ProjectDetail>
)
