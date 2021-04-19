package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.miti.api.model.Restaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdapter(private val restaurants: List<Restaurant>):
        RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantImage = view.restaurantImage!!
        val restaurantTitle = view.restaurantTitle!!
        val restaurantRating = view.restaurantRating!!
        val restaurantDestination = view.restaurantDestination!!
        val restaurantTags = view.restaurantTags!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_restaurant, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.restaurantTitle.text = restaurant.title
        holder.restaurantDestination.text = restaurant.destination
        holder.restaurantRating.text = restaurant.rating.toString()
        holder.restaurantTags.text = restaurant.tags!!.joinToString(separator = " • ")
        holder.restaurantImage.setImageResource(R.drawable.image_fast_and_delicious)
    }

    override fun getItemCount(): Int = restaurants.size

}
