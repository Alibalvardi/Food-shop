package com.example.alifood

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alifood.databinding.ItemFoodBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdaptor(private val foods: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdaptor.FoodViewHolder>() {


    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindDate(position: Int) {
            binding.itemTxtName.text = foods[position].textName
            binding.itemTxtCity.text = foods[position].textCity
            binding.itemTxtPrice.text = "$ " + foods[position].textPrice + " vip"
            binding.itemTxtRating.text = "(" + foods[position].numOfRating.toString() + " ratings)"
            binding.itemTxtDistance.text = foods[position].textDistance + " miles from you"
            binding.itemRatingBar.rating = foods[position].ratingBar

            Glide.with(binding.root.context)
                .load(foods[position].urlImg)
                .transform(RoundedCornersTransformation(24, 4))
                .into(binding.itemImgMain)

            itemView.setOnClickListener {

                foodEvents.foodClicked(foods[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {

                foodEvents.longFoodClicked(foods[adapterPosition], adapterPosition)
                true

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindDate(position)
    }

    override fun getItemCount(): Int {
        return foods.size
    }


    fun addFood(newFood: Food) {
        foods.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {
        foods.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(newFood: Food, position: Int) {
        foods.set(position, newFood)
        notifyItemChanged(position)
    }

    fun setData(newFoods: ArrayList<Food>) {

        foods.clear()
        foods.addAll(newFoods)
        notifyDataSetChanged()
    }

    interface FoodEvents {
        fun foodClicked(food: Food, adapterPosition: Int)
        fun longFoodClicked(food: Food, adapterPosition: Int)

    }
}




