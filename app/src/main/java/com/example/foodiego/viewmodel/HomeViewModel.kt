package com.example.foodiego.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiego.pojo.MealsByCategoryList
import com.example.foodiego.pojo.MealsByCategory
import com.example.foodiego.pojo.Meal
import com.example.foodiego.pojo.MealList
import com.example.foodiego.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private val _MealsByCategoryLiveData = MutableLiveData<List<MealsByCategory>>()
    val mealsByCategoryLiveData:LiveData<List<MealsByCategory>> = _MealsByCategoryLiveData

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())

            }

        })
    }

    fun getPopularItems(categoryName: String) {
        RetrofitInstance.api.getPopularItems(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    _MealsByCategoryLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(p0: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }
}