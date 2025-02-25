package com.example.foodiego.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiego.pojo.MealsByCategoryList
import com.example.foodiego.pojo.Meal
import com.example.foodiego.pojo.MealList
import com.example.foodiego.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategoryList>>()

    fun getMealDetails(id : String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!= null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())

            }

        })

    }
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData

    }



    fun observePopularItemsLiveData():LiveData<List<MealsByCategoryList>>{
        return popularItemsLiveData
    }
}