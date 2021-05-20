package com.cookMeGood.makeItTasteIt.activity

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.dto.Recipe
import com.api.dto.Restaurant
import com.api.dto.Step
import com.cookMeGood.makeItTasteIt.App
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.RecipeDescriptionDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.IngredientsListAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeRestaurantListAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeStepListAdapter
import com.cookMeGood.makeItTasteIt.container.IntentContainer
import com.cookMeGood.makeItTasteIt.container.LogContainer.RECIPE_ACTIVITY_INSERT_RECIPE
import com.cookMeGood.makeItTasteIt.container.TextContainer
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.getStubIngredientsList
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.getStubRestaurants
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.getWindowHeight
import com.cookMeGood.makeItTasteIt.utils.Mapper
import com.cookMeGood.makeItTasteIt.utils.SaveContentHelper
import com.database.AppDatabase
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.content_recipe_bottom_sheet.*

class RecipeActivity : SuperActivity() {

    private lateinit var currentRecipe: Recipe

    private var stepListListAdapter: RecipeStepListAdapter? = null
    private var ingredientsListAdapter: IngredientsListAdapter? = null
    private var recipeRestaurantListAdapter: RecipeRestaurantListAdapter? = null

    private var portions = 1
    private var stepList = mutableListOf<Step>()
    private var recipeDialog: RecipeDescriptionDialogAdapter? = null
    private var ingredientsList = getStubIngredientsList()
    private var restaurantList: List<Restaurant> = getStubRestaurants()
    private val data = arrayOf("Помидоры", "Салат", "Хлеб", "Майонез", "Чеснок", "Сыр", "Укроп", "Лук")
    private val amount = arrayOf("400г", "200г", "1 буханка", "200г", "2 головки", "300г", "50г", "50г")

    private var database: AppDatabase? = null

    override fun setAttr() = setLayout(R.layout.activity_recipe)

    override fun initInterface() {

        database = App.instance.getDataBase()

        val screenHeight = getWindowHeight(windowManager)
        currentRecipe = intent.extras!!
                .getSerializable(IntentContainer.INTENT_RECIPE) as Recipe
        val bottomSheetBehavior = BottomSheetBehavior.from(recipeBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        setSupportActionBar(recipeToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = currentRecipe.name

        recipeCalories.text = currentRecipe.calorieContent!!.calories.toString()
        recipeCarbo.text = currentRecipe.calorieContent!!.carbohydrates.toString()
        recipeFats.text = currentRecipe.calorieContent!!.fat.toString()
        recipeProteins.text = currentRecipe.calorieContent!!.protein.toString()

        recipeTitleTextView.text = currentRecipe.name
        recipeDescription.text = currentRecipe.description
        recipeImageView.setImageResource(R.drawable.image_auth_background)

        recipeDialog = RecipeDescriptionDialogAdapter(currentRecipe.description!!)

        stepListListAdapter = RecipeStepListAdapter(stepList)
        recipeStepList.layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeStepList.adapter = stepListListAdapter

        ingredientsListAdapter = IngredientsListAdapter(ingredientsList)
        recipeIngredientsList.layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeIngredientsList.adapter = ingredientsListAdapter

        recipeRestaurantListAdapter = RecipeRestaurantListAdapter(restaurantList)
        recipeRestaurantsList.layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.VERTICAL, false)
        recipeRestaurantsList.adapter = recipeRestaurantListAdapter

        recipeTitleTextView.measure(0, 0)
        recipeKBJULayout.measure(0, 0)
        recipeDescription.measure(0, 0)

        setPortionPicker()
        clickRecipe()
        setStepList()
        onChangeSheetHeight(screenHeight / 10 * 8)

        bottomSheetBehavior.addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        recipeImageView.translationY =
                                recipeImageView.height.toFloat() / -2 * slideOffset
                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {}

                })

        recipePortionPicker.setOnValueChangedListener { _, _, newVal -> portions = newVal }

        recipeButton.setOnClickListener { clickRecipe() }
        restaurantsButton.setOnClickListener { clickRestaurant() }
        ingredButton.setOnClickListener { clickIngredient() }

        btnDescription.setOnClickListener {
            recipeDialog!!.show(supportFragmentManager, "Recipe dialog")
        }

        recipeDescription.post {

            val peekHeight = recipeTitleTextView.measuredHeight +
                    recipeKBJULayout.measuredHeight +
                    recipeDescription.measuredHeight +
                    ContextUtils.convertDpToPixel(32, applicationContext)

            bottomSheetBehavior.setPeekHeight(peekHeight, true)
        }
    }

    private fun clickRecipe() {

        recipeButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        recipeButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.colorWhite))
        ingredButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        ingredButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))
        restaurantsButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        restaurantsButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))

        recipeStepList.visibility = View.VISIBLE
        recipeIngredientsList.visibility = View.GONE
        recipeRestaurantsList.visibility = View.GONE
        recipePortionPicker.visibility = View.GONE
    }

    private fun clickIngredient() {

        recipeButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        recipeButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))
        ingredButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        ingredButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.colorWhite))
        restaurantsButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        restaurantsButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.VISIBLE
        recipeRestaurantsList.visibility = View.GONE
        recipePortionPicker.visibility = View.VISIBLE
    }

    private fun clickRestaurant() {

        recipeButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        recipeButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))
        ingredButton.setBackgroundResource(R.drawable.shape_button_rounded_white)
        ingredButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.primaryColor))
        restaurantsButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
        restaurantsButton.setTextColor(
                ContextCompat.getColor(applicationContext, R.color.colorWhite))

        recipeStepList.visibility = View.GONE
        recipeIngredientsList.visibility = View.GONE
        recipePortionPicker.visibility = View.GONE
        recipeRestaurantsList.visibility = View.VISIBLE
    }

    private fun setStepList() {

        if (stepList.isNullOrEmpty()) {
            for (i in 1..4) {
                stepList.add(Step(
                        i,
                        "Неторопясь нарезаем вкусненькую отваренную курочку"))
            }
        }
    }

    private fun onChangeSheetHeight(pixels: Int) {
        recipeBottomSheet.layoutParams.height = pixels
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.recipe_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                val imageUri = SaveContentHelper.saveRecipeImageToInternalStorage(
                        applicationContext, R.drawable.image_recipe_background, currentRecipe.id!!)
                val currentRecipeModel = Mapper.recipeDtoToRecipeModel(currentRecipe)
                currentRecipeModel.image = imageUri.toString()
                database!!.recipeDao().insert(currentRecipeModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DisposableSingleObserver<Long>() {

                            override fun onSuccess(t: Long) {
                                Log.i(RECIPE_ACTIVITY_INSERT_RECIPE, "Successfully inserted recipe into DB.")
                                ContextUtils.goShortToast(applicationContext, TextContainer.RECIPE_ACTIVITY_ADD_TO_CART)
                            }

                            override fun onError(e: Throwable) {
                                Log.i(RECIPE_ACTIVITY_INSERT_RECIPE, "Failed to insert recipe into DB!")
                            }

                        })

            }
            R.id.action_like -> {
                ContextUtils.goShortToast(applicationContext, "Нравится!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setPortionPicker() {

        recipePortionPicker.maxValue = 100
        recipePortionPicker.minValue = 1
        recipePortionPicker.setFormatter { i -> "Порций: $i" }
        recipePortionPicker.setSelectedTextColorResource(R.color.primaryColor)
        recipePortionPicker.textSize = resources.getDimension(R.dimen.portionText)
        recipePortionPicker.selectedTextSize = resources.getDimension(R.dimen.portionTextSelected)
        recipePortionPicker.setBackgroundColor(
                ContextCompat.getColor(applicationContext, R.color.colorWhite))
        recipePortionPicker.dividerColor =
                ContextCompat.getColor(applicationContext, R.color.colorBlack80)
    }
}
