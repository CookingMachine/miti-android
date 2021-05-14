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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.api.dto.Ingredient
import com.cookMeGood.makeItTasteIt.adapter.dialog.CartRemoveDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.OnCartUpdateListener
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.database.model.RecipeModel
import kotlinx.android.synthetic.main.item_cart_recipe.view.*

class CartRecipeListAdapter(private var recipes: List<RecipeModel>,
                            var supportFragmentManager: FragmentManager
): RecyclerView.Adapter<CartRecipeListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private var cartDialogAdapter: CartRemoveDialogAdapter? = null

    private val ingredients = arrayListOf<Ingredient>()
    private var expandedList = arrayListOf<Int>()
    private var originalHeight: Int = -1
    private var expandedHeight: Int = -1

    private val cartUpdateListListener = object : OnCartUpdateListener {
        override fun update(recipe: RecipeModel) {
            (recipes as ArrayList).remove(recipe)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
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
            setIngredientsData(holder, recipe)

            view.doOnPreDraw {
                expandedHeight = originalHeight +
                        ContextUtils.convertDpToPixel(32 * ingredients.size, context) + 60
                list.visibility = View.GONE
            }
        }

        //TODO добавить картинку
        holder.name.text = recipe.name

        holder.layout.setOnClickListener {
            expand(holder, !expandedList.contains(position))
        }

        holder.button.setOnClickListener {
            if (cartDialogAdapter == null) {
                cartDialogAdapter = CartRemoveDialogAdapter(recipe, cartUpdateListListener)
            }
            cartDialogAdapter!!.show(supportFragmentManager, "Cart Dialog")
        }
    }

    private inline fun getValueAnimator(
            forward: Boolean = true,
            duration: Long,
            interpolator: TimeInterpolator,
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
            holder.layout.layoutParams.height =
                    (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
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

    private fun setIngredientsData(holder: ViewHolder, recipe: RecipeModel) {
        if (ingredients.isEmpty()) {
            ingredients.add(Ingredient("Томаты", "1 шт"))
            ingredients.add(Ingredient("Салями", "100гр"))
            ingredients.add(Ingredient("Сыр", "200гр"))
            ingredients.add(Ingredient("Лук", "1 головка"))
        }
        if (holder.ingredientListAdapter == null) {
            holder.ingredientListAdapter = CartIngredientListAdapter(context, ingredients)
            holder.ingredientsList.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false)
            holder.ingredientsList.adapter = holder.ingredientListAdapter
        }
    }

    fun updateList(newRecipeList: List<RecipeModel>) {
        recipes = newRecipeList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.cartRecipeImage!!
        val name = view.cartRecipeTitle!!
        val button = view.cartRemoveButton!!
        val layout = view.cartRecipeLayout!!
        val divider = view.cartRecipeDivider!!
        val ingredientsList = view.cartIngredientList!!
        var ingredientListAdapter: CartIngredientListAdapter? = null
    }
}