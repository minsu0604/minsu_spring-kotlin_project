package com.example.myapplication.dto

data class User(
    var userId:String,
    var userPw:String,
    var name:String,
    var email:String,
    var address:String,
    var projectList:List<Project>,
    var supportList:List<Support>,
    var favoriteList:List<Favorite>
)
