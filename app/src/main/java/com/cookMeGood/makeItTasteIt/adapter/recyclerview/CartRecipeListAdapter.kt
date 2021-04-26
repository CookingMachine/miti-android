package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.model.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.api.model.Ingredient
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.item_cart_recipe.view.*

class CartRecipeListAdapter(private val recipes: List<Recipe>, val context: Context):
        RecyclerView.Adapter<CartRecipeListAdapter.ViewHolder>() {

    private val ingredients = arrayListOf<Ingredient>()
    private lateinit var recyclerView: RecyclerView

    private var expandedList = arrayListOf<Int>()
    private var originalHeight : Int = -1
    private var expandedHeight : Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_cart_recipe,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipes[position]

        val list = holder.ingredientsList

        holder.layout.doOnLayout { view ->
            originalHeight = view.height
            list.visibility = View.VISIBLE
            setIngredientsData(holder)

            view.doOnPreDraw {
                expandedHeight = originalHeight + HelpUtils.convertDpToPixel(32 * ingredients.size, context) + 60
                list.visibility = View.GONE
            }
        }

        //TODO добавить картинку
        holder.name.text = recipe.name

        holder.layout.setOnClickListener {
            expand(holder, !expandedList.contains(position))
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

    private fun expand(holder: ViewHolder, expand: Boolean) {

        val animator = getValueAnimator(
                expand, 300, AccelerateDecelerateInterpolator()
        ) { progress ->
            holder.layout.layoutParams.height = (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
            holder.layout.requestLayout()

            holder.divider.translationY = (expandedHeight - originalHeight - 30) * progress
            holder.divider.rotation = 180 * progress
        }

        if (expand) animator.doOnStart {
            holder.ingredientsList.visibility = View.VISIBLE
            expandedList.add(holder.bindingAdapterPosition)
        }
        else animator.doOnEnd {
            holder.ingredientsList.visibility = View.GONE
            expandedList.remove(holder.bindingAdapterPosition)
        }

        animator.start()
    }

    private fun setIngredientsData(holder: ViewHolder){
        if(ingredients.isEmpty()) {
            ingredients.add(Ingredient("Томаты", "1 шт"))
            ingredients.add(Ingredient("Салями", "100гр"))
            ingredients.add(Ingredient("Сыр", "200гр"))
            ingredients.add(Ingredient("Лук", "1 головка"))
        }
        if(holder.ingredientListAdapter == null){
            holder.ingredientListAdapter = CartIngredientListAdapter(context, ingredients)
            holder.ingredientsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.ingredientsList.adapter = holder.ingredientListAdapter
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.cartRecipeImage!!
        val name = view.cartRecipeTitle!!
        val layout = view.cartRecipeLayout!!
        val divider = view.cartRecipeDivider!!
        val ingredientsList = view.cartIngredientList!!
        var ingredientListAdapter : CartIngredientListAdapter? = null
    }
}