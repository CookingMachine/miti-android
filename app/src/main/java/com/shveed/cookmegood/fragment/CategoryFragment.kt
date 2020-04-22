package com.shveed.cookmegood.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.shveed.cookmegood.R
import com.shveed.cookmegood.activity.RecipeActivity
import com.shveed.cookmegood.adapter.CategoryRecipeAdapter
import com.shveed.cookmegood.adapter.RecipeItemClickListener
import com.shveed.cookmegood.data.dto.Recipe
import java.util.*

class CategoryFragment: Fragment() {
    val recipes = arrayListOf<Recipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.f_category,container,false);
        setRecipeData()
        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecycler)
        recyclerView.addOnItemTouchListener(
                RecipeItemClickListener(context, recyclerView, object: RecipeItemClickListener.OnItemClickListener{
                            override fun onItemClick(view: View?, position: Int) {
                                val name = recipes[position].name
                                val image = Objects.requireNonNull(recipes[position].image)!!.toInt()
                                toRecipe(name!!, image)
                            }

                            override fun onLongItemClick(view: View?, position: Int) {
                                val name = recipes[position].name
                                val image = recipes[position].image!!.toInt()
                                toRecipe(name!!, image)
                            }
                        })
        )
        val adapter = CategoryRecipeAdapter(recipes,context)
        recyclerView.adapter = adapter
        return view
    }

    private fun setRecipeData() {
        recipes.add(Recipe("Борщ", "Подавать теплым", "3:00", java.lang.String.valueOf(R.drawable.img_recipe_background), "Украинская"))
        recipes.add(Recipe("Пиууа", "Для большоц компании", "2:00", java.lang.String.valueOf(R.drawable.img_recipe_background), "Итальянская"))
    }

    private fun toRecipe(name: String, image: Int) {
        val intent = Intent(context, RecipeActivity::class.java)
        intent.putExtra("recipeName", name)
        intent.putExtra("recipeImage", image)
        startActivity(intent)
    }

    fun goToast(output: String?) {
        val errorToast = Toast.makeText(activity,
                output, Toast.LENGTH_SHORT)
        errorToast.show()
    }
}

