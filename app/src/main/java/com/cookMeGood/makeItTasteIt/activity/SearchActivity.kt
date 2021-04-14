package com.cookMeGood.makeItTasteIt.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.ApiService
import com.api.model.Category
import com.api.model.Ingredient
import com.api.model.Recipe
import com.api.model.request.SearchRecipeRequest
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFilterClickListener
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.listener.OnSearchIngredientClickListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SearchContentAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SearchFilterAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SearchIngredientsAdapter
import com.cookMeGood.makeItTasteIt.utils.*
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.getStubCategoryList
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.getStubIngredientsList
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.getStubKitchenList
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.getWindowHeight
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_recipe_bottom_sheet.*
import kotlinx.android.synthetic.main.content_search_bottom_sheet.*
import kotlinx.android.synthetic.main.content_search_ingredients.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class SearchActivity : SuperActivity() {

    private var caloriesValueFrom = 0
    private var caloriesValueTo = 2000
    private var timeValueFrom = 5
    private var timeValueTo = 120
    private var ingredientsCounter = 0

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var searchRecipeRequest = SearchRecipeRequest()
    private var filterCategoriesList = getStubCategoryList()
    private var filterKitchenList = getStubKitchenList()
    private var filterCategoryAdapter: SearchFilterAdapter? = null
    private var filterKitchenAdapter: SearchFilterAdapter? = null
    private var searchContentAdapter: SearchContentAdapter? = null
    private var searchIngredientsAdapter: SearchIngredientsAdapter? = null
    private var clickedIngredientsList: List<Ingredient>? = null
    private var searchContentList = HelpUtils.getStubRecipeList()
    private var searchIngredientsList = getStubIngredientsList()

    private val openRecipeListener = object : OnOpenRecipeListener {
        override fun openRecipe(recipe: Recipe) {
            val intent = Intent(applicationContext, RecipeActivity::class.java)
            intent.putExtra(ConstantContainer.INTENT_RECIPE, recipe)
            startActivity(intent)
        }
    }

    private val onIngredientClickListener = object : OnSearchIngredientClickListener {
        override fun onClick(ingredients: List<Ingredient>) {
            clickedIngredientsList = ingredients
            searchIngredientsCounter.text = clickedIngredientsList!!.size.toString()
        }
    }

    private var onCategoryFilterClickListener = object : OnFilterClickListener {
        override fun onTouch(item: String) {
            searchRecipeRequest.category = item
        }
    }

    private var onKitchenFilterClickListener = object : OnFilterClickListener {
        override fun onTouch(item: String) {
            searchRecipeRequest.kitchen = item
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_search)

    @SuppressLint("SetTextI18n")
    override fun initInterface() {

        setSupportActionBar(searchActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAllCategoriesFromServer()
        //getAllKitchensFromServer()
        //getSearchListFromServer("")

        searchBackContent.measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        bottomSheetBehavior = BottomSheetBehavior.from(searchBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.peekHeight = getWindowHeight(windowManager) -
                searchBackContent.measuredHeight - 400

        searchIngredientsCounter.text = ingredientsCounter.toString()

        searchContentAdapter = SearchContentAdapter(
                searchContentList,
                applicationContext,
                openRecipeListener
        )
        searchActivityContentList.layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.VERTICAL,
                false
        )
        searchActivityContentList.adapter = searchContentAdapter

        filterKitchenAdapter = SearchFilterAdapter(filterKitchenList, onKitchenFilterClickListener)
        searchKitchenList.layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        searchKitchenList.adapter = filterKitchenAdapter

        searchIngredientsAdapter =
                SearchIngredientsAdapter(searchIngredientsList, onIngredientClickListener)
        searchIngredientsRecyclerView.layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.VERTICAL,
                false
        )
        searchIngredientsRecyclerView.adapter = searchIngredientsAdapter

        searchCaloriesRangeSlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter "${value.roundToInt()}Cal"
        }

        searchCaloriesRangeSlider.addOnChangeListener { slider, _, _ ->
            caloriesValueFrom = slider.values.first().toInt()
            caloriesValueTo = slider.values.last().toInt()

            if (caloriesValueFrom == caloriesValueTo) {
                searchCaloriesValue.text = "$caloriesValueFrom"
            } else {
                searchCaloriesValue.text = "от $caloriesValueFrom до $caloriesValueTo"
            }

            searchRecipeRequest.caloriesMin = caloriesValueFrom
            searchRecipeRequest.caloriesMax = caloriesValueTo
        }

        searchTimeRangeSlider.addOnChangeListener { slider, _, _ ->
            timeValueFrom = slider.values.first().toInt()
            timeValueTo = slider.values.last().toInt()

            if (timeValueFrom == timeValueTo) {
                searchTimeValue.text = "$timeValueFrom минут"
            } else {
                searchTimeValue.text = "от $timeValueFrom до $timeValueTo минут"
            }

            searchRecipeRequest.timeMin = timeValueFrom
            searchRecipeRequest.timeMax = timeValueTo
        }

        searchIngredients.setOnClickListener {
            if (searchIngredientsContent.visibility == View.GONE) {
                searchActivityContentList.visibility = View.GONE
                searchNotFound.visibility = View.GONE
                searchIngredientsContent.visibility = View.VISIBLE
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        searchIngredientCloseButton.setOnClickListener {
            searchIngredientsContent.visibility = View.GONE
            searchActivityContentList.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onChooseBottomSheetHeight()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            bottomSheetBehavior.state =
                    when (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        true -> BottomSheetBehavior.STATE_COLLAPSED
                        false -> BottomSheetBehavior.STATE_EXPANDED
                    }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onChooseBottomSheetHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (window.decorView.rootWindowInsets.displayCutout != null) {
                setSheetHeight(
                        getWindowHeight(windowManager) -
                                HelpUtils.getActionBarSize(applicationContext))
            } else {
                setSheetHeight(
                        getWindowHeight(windowManager) -
                                HelpUtils.getStatusBarHeightInPixels(resources) -
                                HelpUtils.getActionBarSize(applicationContext))
            }
        } else {
            setSheetHeight(
                    getWindowHeight(windowManager) -
                            HelpUtils.getStatusBarHeightInPixels(resources) -
                            HelpUtils.getActionBarSize(applicationContext))
        }
    }

    private fun getSearchListFromServer(sort: String) {
        ApiService.getApi(applicationContext)
                .getRecipesByCriteria(searchRecipeRequest, sort)
                .enqueue(object : Callback<List<Recipe>> {
                    override fun onResponse(
                            call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                        if (response.isSuccessful) {
                            searchContentList = response.body() ?: emptyList()

                            if (searchContentList.isEmpty()) {
                                searchActivityContentList.visibility = View.GONE
                                searchNotFound.visibility = View.VISIBLE
                            } else {
                                searchActivityContentList.visibility = View.VISIBLE
                                searchNotFound.visibility = View.GONE
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        searchActivityContentList.visibility = View.GONE
                        searchNotFound.visibility = View.VISIBLE
                    }

                })
    }

    private fun getAllCategoriesFromServer() {
        ApiService.getApi(applicationContext)
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(
                            call: Call<List<Category>>, response: Response<List<Category>>) {
                        if (response.isSuccessful) {
                            filterCategoriesList = response.body()!!.map { category ->
                                category.name!!
                            }
                            filterCategoryAdapter = SearchFilterAdapter(filterCategoriesList, onCategoryFilterClickListener)
                            searchCategoryList.layoutManager = LinearLayoutManager(
                                    applicationContext,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                            )
                            searchCategoryList.adapter = filterCategoryAdapter
                        }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
    }

    private fun setSheetHeight(pixels: Int) {
        searchBottomSheet.layoutParams.height = pixels
    }
}