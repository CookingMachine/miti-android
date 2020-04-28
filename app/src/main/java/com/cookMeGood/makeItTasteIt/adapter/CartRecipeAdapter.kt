package com.cookMeGood.makeItTasteIt.adapter

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.data.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.data.dto.Ingredient
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.utils.SlideAnimation
import kotlinx.android.synthetic.main.item_cart_recipe.view.*

class CartRecipeAdapter(private val recipes: List<Recipe>, val context: Context): RecyclerView.Adapter<CartRecipeAdapter.ViewHolder>() {

    private val ingredients = arrayListOf<Ingredient>()

    private var expandedModel: Recipe? = null

    private var originalHeight : Int = 420
    private var expandedHeight : Int = 560

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_cart_recipe,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipes[position]

        holder.image.setImageResource(recipe.image!!.toInt())
        holder.name.text = recipe.name

        holder.layout.setOnClickListener {

            if (expandedModel == null) {

                expand(holder, expand = true, animate = true)
                expandedModel = recipe

            } else if (expandedModel == recipe) {

                expand(holder, expand = false, animate = true)
                expandedModel = null

            } else {

                expand(holder, expand = true, animate = true)
                expandedModel = recipe

            }
        }
    }


    private inline fun getValueAnimator(forward: Boolean = true, duration: Long, interpolator: TimeInterpolator,
                                crossinline updateListener: (progress: Float) -> Unit
    ): ValueAnimator {
        val a =
                if (forward) ValueAnimator.ofFloat(0f, 1f)
                else ValueAnimator.ofFloat(1f, 0f)
        a.addUpdateListener { updateListener(it.animatedValue as Float) }
        a.duration = duration
        a.interpolator = interpolator
        return a
    }

    private fun expand(holder: ViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val animator = getValueAnimator(
                    expand, 300, AccelerateDecelerateInterpolator()
            ) { progress ->
                holder.layout.layoutParams.height =
                        (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
//                holder.layout.layoutParams.width =
//                        (originalWidth + (expandedWidth - originalWidth) * progress).toInt()
                holder.layout.requestLayout()

                holder.divider.rotation = 180 * progress
            }

            if (expand) animator.doOnStart { holder.ingredientsList.visibility = View.VISIBLE }
            else animator.doOnEnd { holder.ingredientsList.visibility = View.GONE }

            animator.start()
        } else {
            // ... Left out for brevity
        }
    }

    private fun setIngredientsData(holder: ViewHolder){
        if(ingredients.isNotEmpty()) {
            ingredients.add(Ingredient("Томаты", "1 шт"))
            ingredients.add(Ingredient("Салями", "100гр"))
            ingredients.add(Ingredient("Сыр", "200гр"))
            ingredients.add(Ingredient("Лук", "1 головка"))

            holder.ingredientsAdapter = CartIngredientsAdapter(context, ingredients)
            holder.ingredientsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.ingredientsList.adapter = holder.ingredientsAdapter
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.cartRecipeImage!!
        val name = view.cartRecipeTitle!!
        val layout = view.cartRecipeLayout!!
        val divider = view.cartRecipeDivider!!
        val ingredientsList = view.cartIngredientList!!
        var ingredientsAdapter : CartIngredientsAdapter? = null
        var isClosed = true
    }
}