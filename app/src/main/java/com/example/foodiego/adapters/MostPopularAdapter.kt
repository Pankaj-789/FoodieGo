package com.example.foodiego.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodiego.databinding.PopularItemsBinding
import com.example.foodiego.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularItemsViewHolder>() {
    lateinit var onItemClick:((MealsByCategory)-> Unit)
    private val mealList = mutableListOf<MealsByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(list: List<MealsByCategory>) {
        mealList.clear()
        mealList.addAll(list)
        notifyDataSetChanged()
    }

    class PopularItemsViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemsViewHolder {
        return PopularItemsViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return mealList.size

    }

    override fun onBindViewHolder(holder: PopularItemsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularItemsIv)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }
    }
}