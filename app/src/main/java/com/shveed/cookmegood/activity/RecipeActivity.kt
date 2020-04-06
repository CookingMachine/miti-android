package com.shveed.cookmegood.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shveed.cookmegood.R
import com.shveed.cookmegood.data.dto.Ingredient
import com.shveed.cookmegood.fragment.IngredientFragment
import com.shveed.cookmegood.fragment.KbjuFragment
import com.shveed.cookmegood.fragment.RecipeFragment
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.layout_recipe_bottom_sheet.*
import java.util.*

class RecipeActivity : AppCompatActivity(){

    private var portions = 1
    
    private var recipeFragment: RecipeFragment? = null
    private var ingredientFragment: IngredientFragment? = null
    private var kbjuFragment: KbjuFragment? = null
    private var fragmentTransaction: FragmentTransaction? = null

    private val buyMap = HashMap<Ingredient, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val name: String = intent.extras?.get("recipeName").toString()
        val image: Int = Integer.valueOf(intent.extras?.get("recipeImage").toString())
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)

        ingredientFragment = IngredientFragment()
        recipeFragment = RecipeFragment()
        kbjuFragment = KbjuFragment()

        transaction.add(R.id.frameRecipe, recipeFragment!!)
        transaction.commit()

        recipeTitleTextView.text = name
        recipeImageView.setImageResource(image)
        
        recipeButton.setOnClickListener { clickRecipe()  }
        kbjuButton.setOnClickListener { clickKbju()  }
        ingredButton.setOnClickListener { clickIngred()  }

    }
    private fun clickRecipe() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button_inversed)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.frameRecipe, recipeFragment!!)
        fragmentTransaction!!.commit()
    }

    private fun clickIngred() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button_inversed)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.frameRecipe, ingredientFragment!!)
        fragmentTransaction!!.commit()
    }

    private fun clickKbju() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button_inversed)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.frameRecipe, kbjuFragment!!)
        fragmentTransaction!!.commit()
    }

    fun countPortion(view: View) {
        val line: String
        if (view.id == R.id.minusButton) {
            if (portions > 1) {
                portions--
                line = "Порции: $portions"
                countPor.text = line
            }
        } else if (view.id == R.id.plusButton) {
            portions++
            line = "Порции: $portions"
            countPor.text = line
        }
    }

}