package com.joseluisng.imagenesfinteres

import com.joseluisng.imagenesfinteres.endpoint.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val baseUrl = "http://192.168.0.103:3000"

val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.103:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

val service = retrofit.create<ApiService>(ApiService::class.java)