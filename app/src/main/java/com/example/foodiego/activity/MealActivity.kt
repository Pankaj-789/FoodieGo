package com.example.foodiego.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodiego.R
import com.example.foodiego.databinding.ActivityMealBinding
import com.example.foodiego.fragments.HomeFragment
import com.example.foodiego.pojo.Meal
import com.example.foodiego.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var mealMvvm : MealViewModel
    private lateinit var youtubeLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetails(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()

    } 

    private fun onYoutubeImageClick() {
        binding.youtubeIv.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                binding.mealCategoriesTv.text = "Categories : ${meal.strCategory}"
                binding.mealAreaTv.text = "Area : ${meal.strArea}"
                binding.instructionDetailsTv.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.mealDetailsIv)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()

    }

    fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.floatingActionButton.visibility = View.INVISIBLE
        binding.instructionTv.visibility = View.INVISIBLE
        binding.mealCategoriesTv.visibility = View.INVISIBLE
        binding.mealAreaTv.visibility = View.INVISIBLE
        binding.youtubeIv.visibility = View.INVISIBLE

    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.floatingActionButton.visibility = View.VISIBLE
        binding.instructionTv.visibility = View.VISIBLE
        binding.mealCategoriesTv.visibility = View.VISIBLE
        binding.mealAreaTv.visibility = View.VISIBLE
        binding.youtubeIv.visibility = View.VISIBLE


    }

}