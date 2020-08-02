package com.cookMeGood.makeItTasteIt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.dto.Step
import kotlinx.android.synthetic.main.item_recipe_step.view.*

class RecipeStepAdapter(private val steps: List<Step>, val context: Context):
        RecyclerView.Adapter<RecipeStepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recipe_step, parent, false))
    }

    override fun getItemCount() = steps.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = steps[position]

        holder.stepNumber.text = item.stepNumber.toString()
        holder.stepDescription.text = item.description
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val stepNumber = view.recipeStepNumber!!
        val stepDescription = view.recipeStepDescription!!

    }
}