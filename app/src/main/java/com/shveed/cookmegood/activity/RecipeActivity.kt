package com.shveed.cookmegood.activity

import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shveed.cookmegood.R
import com.shveed.cookmegood.adapter.IngredientsListAdapter
import com.shveed.cookmegood.adapter.RecipeStepAdapter
import com.shveed.cookmegood.data.dto.Ingredient
import com.shveed.cookmegood.data.dto.Step
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.layout_recipe_bottom_sheet.*
import java.util.*

class RecipeActivity : AppCompatActivity(){

    private var portions = 1

    private var steps = mutableListOf<Step>()

    private var stepListAdapter: RecipeStepAdapter? = null
    private var ingredientsListAdapter: IngredientsListAdapter? = null

    private var ingredients = arrayListOf<Ingredient>()
    private val data = arrayOf("Помидоры", "Салат", "Хлеб", "Майонез", "Чеснок", "Сыр", "Укроп", "Лук")
    private val amount = arrayOf("400г", "200г", "1 буханка", "200г", "2 головки", "300г", "50г", "50г")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        setSupportActionBar(recipeToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name: String = intent.extras?.get("recipeName").toString()
        val image: Int = Integer.valueOf(intent.extras?.get("recipeImage").toString())

        val peekHeight = recipeTitleTextView.layoutParams.height +
                recipeText.layoutParams.height +
                recipeButtonsLayout.layoutParams.height

        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenHeight = point.y

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setPeekHeight(peekHeight, true)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        onChangeSheetState(screenHeight / 10 * 6)

        clickRecipe()

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                recipeImageView.translationY = recipeImageView.height.toFloat() / 2 * slideOffset * -1
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

        })

        stepListAdapter = RecipeStepAdapter(applicationContext, steps)
        recipeStepList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeStepList.adapter = stepListAdapter

        ingredientsListAdapter = IngredientsListAdapter(applicationContext, ingredients)
        recipeIngredientsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeIngredientsList.adapter = ingredientsListAdapter

        recipeTitleTextView.text = name
        recipeImageView.setImageResource(image)
        
        recipeButton.setOnClickListener { clickRecipe()  }
        kbjuButton.setOnClickListener { clickKbju()  }
        ingredButton.setOnClickListener { clickIngred()  }

    }
    private fun clickRecipe() {

        recipeButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))

        recipeStepList.visibility = View.VISIBLE
        recipeIngredientsList.visibility = View.GONE
        recipeKBJU.visibility = View.GONE

        setStepList()
    }

    private fun clickIngred() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        ingredButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.VISIBLE
        recipeKBJU.visibility = View.GONE

        setIngredientsList()
    }

    private fun clickKbju() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.objectsColor))
        kbjuButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.backgroundColor))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.GONE
        recipeKBJU.visibility = View.VISIBLE
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

    private fun setStepList() {
        if(steps.isNullOrEmpty()) {
            for (i in 1..4) {
                steps.add(Step(i,
                        "Неторопясь нарезаем вкусненькую отваренную курочку",
                        "Нарезаем курицу"))
            }
        }
    }

    private fun setIngredientsList(){
        if(ingredients.isNullOrEmpty()) {
            for (i in 0..7) {
                val ingredient = Ingredient(data[i], amount[i])
                ingredients.add(ingredient)
            }
        }
    }

    private fun onChangeSheetState(pixels: Int){
        bottomSheet.layoutParams.height = pixels
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_basket -> {

            }
            R.id.action_like -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

}