package com.cookMeGood.makeItTasteIt.view.activity

import android.graphics.Point
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.RecipeDescriptionDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.IngredientsListAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeStepListAdapter
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient
import com.cookMeGood.makeItTasteIt.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.api.dto.Step
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.utils.IntentContainer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.content_recipe_bottom_sheet.*

class RecipeActivity : SuperActivity() {

    private var portions = 1
    private var stepList = mutableListOf<Step>()
    private var stepListListAdapter: RecipeStepListAdapter? = null
    private var ingredientsListAdapter: IngredientsListAdapter? = null
    private var ingredientsList = arrayListOf<Ingredient>()
    private val data = arrayOf("Помидоры", "Салат", "Хлеб", "Майонез", "Чеснок", "Сыр", "Укроп", "Лук")
    private val amount = arrayOf("400г", "200г", "1 буханка", "200г", "2 головки", "300г", "50г", "50г")

    override fun setAttr() = setLayout(R.layout.activity_recipe)

    override fun initInterface() {

        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenHeight = point.y
        val currentRecipe = intent.extras!!.getSerializable(IntentContainer.INTENT_RECIPE) as Recipe
        val recipeDialog = RecipeDescriptionDialogAdapter()

        setSupportActionBar(recipeToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = currentRecipe.name

        btnDescription.setOnClickListener {
            recipeDialog.show(supportFragmentManager, "Recipe dialog")
        }

        recipeTitleTextView.text = currentRecipe.name
        recipeDescription.text = currentRecipe.description
        recipeImageView.setImageResource(R.drawable.image_recipe_background)

        stepListListAdapter = RecipeStepListAdapter(stepList, applicationContext)
        recipeStepList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeStepList.adapter = stepListListAdapter

        ingredientsListAdapter = IngredientsListAdapter(applicationContext, ingredientsList)
        recipeIngredientsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeIngredientsList.adapter = ingredientsListAdapter

        recipeTitleTextView.measure(0, 0)
        recipeKBJULayout.measure(0, 0)
        recipeDescription.measure(0, 0)

        setPortionPicker()
        clickRecipe()
        setKBJUValues()

        val bottomSheetBehavior = BottomSheetBehavior.from(recipeBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        onChangeSheetHeight(screenHeight / 10 * 8)

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                recipeImageView.translationY = recipeImageView.height.toFloat() / 2 * slideOffset * -1
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {}

        })

        recipePortionPicker.setOnValueChangedListener { _, _, newVal -> portions = newVal }

        recipeButton.setOnClickListener { clickRecipe()  }
        kbjuButton.setOnClickListener { clickRestaurant()  }
        ingredButton.setOnClickListener { clickIngred()  }

        recipeDescription.post {

            val peekHeight = recipeTitleTextView.measuredHeight +
                    recipeKBJULayout.measuredHeight +
                    recipeDescription.measuredHeight +
                    HelpUtils.convertDpToPixel(32, applicationContext)

            bottomSheetBehavior.setPeekHeight(peekHeight, true)
        }

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
        recipeRestaurants.visibility = View.GONE
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
        recipeRestaurants.visibility = View.GONE
        recipePortionPicker.visibility = View.VISIBLE

        setIngredientsList()
    }

    private fun clickRestaurant() {

        recipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
        recipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        ingredButton.setBackgroundResource(R.drawable.rounded_corners_button)
        ingredButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
        kbjuButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        kbjuButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.GONE
        recipeRestaurants.visibility = View.VISIBLE
        recipePortionPicker.visibility = View.GONE
    }

    private fun setStepList() {
        if(stepList.isNullOrEmpty()) {
            for (i in 1..4) {
                stepList.add(Step("Нарезаем курицу",
                        i,
                        "Неторопясь нарезаем вкусненькую отваренную курочку"))
            }
        }
    }

    private fun setIngredientsList(){
        if(ingredientsList.isNullOrEmpty()) {
            for (i in 0..7) {
                val ingredient = Ingredient(data[i], amount[i])
                ingredientsList.add(ingredient)
            }
        }
    }

    private fun onChangeSheetHeight(pixels: Int){
        recipeBottomSheet.layoutParams.height = pixels
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_basket -> {
                HelpUtils.goToast(applicationContext, "Добавлено в корзину")
            }
            R.id.action_like -> {
                HelpUtils.goToast(applicationContext, "Нравится!")
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

    private fun setKBJUValues(){
        recipeCalories.text = "100"
        recipeProteins.text = "69"
        recipeCarbo.text = "10"
        recipeFats.text = "40"
    }
}