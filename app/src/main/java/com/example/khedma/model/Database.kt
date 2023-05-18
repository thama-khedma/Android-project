package com.example.khedma.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Database {
    companion object {

        private const val BaseURL: String = "http://192.168.1.166:3000/"
        fun getRetroBuilder(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}