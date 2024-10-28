package com.example.myapplication.retrofitPacket

data class UserFavoritePacket(
    var userId:String,
    var userPw:String,
    var name:String,
    var email:String,
    var address:String,
    var favoriteList:List<ProjectDetail>,
    var supportList:List<ProjectDetail>
)
