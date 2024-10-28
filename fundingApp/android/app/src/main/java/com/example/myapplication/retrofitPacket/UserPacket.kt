package com.example.myapplication.retrofitPacket

import java.io.Serializable

data class UserPacket (
    var userId:String,
    var userPw:String,
    var name:String,
    var email:String,
    var address:String,
): Serializable
