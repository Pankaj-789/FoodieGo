package com.example.foodiego.retrofit

import com.example.foodiego.pojo.Meal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL ="https://www.themealdb.com/api/json/v1/1/"

object RetrofitInstance {

    val api : MealApi by lazy{
         Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(MealApi::class.java)
    }
}