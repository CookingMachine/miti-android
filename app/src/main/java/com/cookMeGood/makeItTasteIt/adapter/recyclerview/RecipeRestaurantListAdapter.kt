package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.api.dto.Restaurant
import kotlinx.android.synthetic.main.item_recipe_restaurant.view.*

class RecipeRestaurantListAdapter(private val restaurants: List<Restaurant>) :
        RecyclerView.Adapter<RecipeRestaurantListAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantImage = view.recipeActivityRestaurantImage!!
        val restaurantTitle = view.recipeActivityRestaurantTitle!!
        val restaurantKitchen = view.recipeActivityRestaurantKitchen!!
        val restaurantAverageBill = view.recipeActivityRestaurantAverageBill!!
        val restaurantMetro = view.recipeActivityRestaurantMetro!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_recipe_restaurant, parent, false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val restaurant = restaurants[position]

        holder.restaurantImage.setImageResource(R.drawable.image_fast_and_delicious)
        holder.restaurantTitle.text = restaurant.title
        holder.restaurantKitchen.text = restaurant.kitchen
        holder.restaurantAverageBill.text = "Средний чек: ${restaurant.averageBill}"

        holder.restaurantMetro.text = restaurant.metroStation!!.title
        holder.restaurantMetro.lineNumber = restaurant.metroStation!!.id!!
    }

    override fun getItemCount(): Int = restaurants.size
}