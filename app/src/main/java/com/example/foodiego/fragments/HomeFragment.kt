package com.example.foodiego.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodiego.activity.MealActivity
import com.example.foodiego.adapters.MostPopularAdapter
import com.example.foodiego.databinding.FragmentHomeBinding
import com.example.foodiego.pojo.CategoryMeals
import com.example.foodiego.pojo.Meal
import com.example.foodiego.viewmodel.HomeViewModel
import com.example.foodiego.viewmodel.MealViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var mealMvvm : MealViewModel
    private lateinit var randomMeal: Meal

    companion object{
        const val MEAL_ID = "com.example.foodiego.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodiego.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodiego.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        super.onCreate(savedInstanceState)

        popularItemsAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

//        homeMvvm.getPopularItems("SeaFood")
//        observePopularItemsLiveData()

        preparePopularItemsRecyclerView()

    }

    private fun preparePopularItemsRecyclerView() {
        binding.popularItemsRv.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun onRandomMealClick(){
        binding.mealIv.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra( MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }

    }
//    private fun observePopularItemsLiveData() {
//        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner)
//        {
//            mealList ->
//
//        }
//    }






    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.mealIv)

            this.randomMeal = meal
        }
    }

}