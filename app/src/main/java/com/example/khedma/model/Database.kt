package com.example.khedma.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Database {
    companion object {

        private const val BaseURL: String = "http://172.17.5.97:3000/"
        fun getRetroBuilder(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}