package com.cookMeGood.makeItTasteIt.activity

import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.IngredientsListAdapter
import com.cookMeGood.makeItTasteIt.adapter.RecipeStepAdapter
import com.cookMeGood.makeItTasteIt.data.dto.Ingredient
import com.cookMeGood.makeItTasteIt.data.dto.Step
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.layout_recipe_bottom_sheet.*

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

        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenHeight = point.y

        val name: String = intent.extras?.get("recipeName").toString()
        val image: Int = Integer.valueOf(intent.extras?.get("recipeImage").toString())
        recipeTitleTextView.text = name
        recipeImageView.setImageResource(image)

        setPortionPicker()

        stepListAdapter = RecipeStepAdapter(steps, applicationContext)
        recipeStepList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeStepList.adapter = stepListAdapter

        ingredientsListAdapter = IngredientsListAdapter(applicationContext, ingredients)
        recipeIngredientsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeIngredientsList.adapter = ingredientsListAdapter

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        onChangeSheetHeight(screenHeight / 10 * 7)

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                recipeImageView.translationY = recipeImageView.height.toFloat() / 2 * slideOffset * -1
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

        })

        clickRecipe()
        
        recipeButton.setOnClickListener { clickRecipe()  }
        kbjuButton.setOnClickListener { clickKbju()  }
        ingredButton.setOnClickListener { clickIngred()  }

        recipeText.post(Runnable(){

            val peekHeight = recipeTitleTextView.layoutParams.height +
                    recipeText.height +
                    recipeButtonsLayout.layoutParams.height

            bottomSheetBehavior.setPeekHeight(peekHeight, true)
        })

    }
    private fun clickRecipe() {

        recipeButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))

        recipeStepList.visibility = View.VISIBLE
        recipeIngredientsList.visibility = View.GONE
        recipeKBJU.visibility = View.GONE

        recipePortionPicker.visibility = View.GONE

        setStepList()
    }

    private fun clickIngred() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        ingredButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
        kbjuButton.setBackgroundResource(R.drawable.rounded_corners_button)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.VISIBLE
        recipeKBJU.visibility = View.GONE

        recipePortionPicker.visibility = View.VISIBLE

        setIngredientsList()
    }

    private fun clickKbju() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        kbjuButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.GONE
        recipeKBJU.visibility = View.VISIBLE

        recipePortionPicker.visibility = View.VISIBLE
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

    private fun onChangeSheetHeight(pixels: Int){
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

    private fun setPortionPicker(){
        recipePortionPicker.maxValue = 100
        recipePortionPicker.minValue = 1
        recipePortionPicker.setFormatter { i -> "Порций: $i" }
        recipePortionPicker.setSelectedTextColorResource(R.color.primaryColor)
        recipePortionPicker.textSize = resources.getDimension(R.dimen.portionText)
        recipePortionPicker.selectedTextSize = resources.getDimension(R.dimen.portionTextSelected)
        recipePortionPicker.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
        recipePortionPicker.dividerColor = ContextCompat.getColor(applicationContext, R.color.colorBlack80)
    }

}