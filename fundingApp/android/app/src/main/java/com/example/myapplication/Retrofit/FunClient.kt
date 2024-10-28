package com.example.myapplication.Retrofit

import com.example.myapplication.utils.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FunClient {
    val retrofit:FunInterface = Retrofit.Builder()
        //TODO: ip 주소 변경 필요
        .baseUrl(Const.SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
        .build()
        .create(FunInterface::class.java)
}