package com.example.foodiego.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiego.pojo.CategoryList
import com.example.foodiego.pojo.Meal
import com.example.foodiego.pojo.MealList
import com.example.foodiego.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!= null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())

            }

        })
    }
//    fun getPopularItems(categoryName : String){
//        RetrofitInstance.api.getPopularItems("SeaFood").enqueue(object : Callback<CategoryList>{
//            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
//                if(response.body() != null){
//                    popularItemsLiveData.value = response.body()!!.meals                }
//            }
//
//            override fun onFailure(p0: Call<CategoryList>, t: Throwable) {
//                Log.d("HomeFragment", t.message.toString())
//            }
//        })
//    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }
}