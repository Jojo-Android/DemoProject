package com.example.demoproject.data.api

import com.example.demoproject.data.model.ResponseProducts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://dummyjson.com/"

interface ProductsService {

    @GET("products")
    suspend fun getProduct(): ResponseProducts

    companion object {
        fun create(): ProductsService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsService::class.java)
    }

}