package com.cookMeGood.makeItTasteIt.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SearchContentAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SearchFilterAdapter
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.dto.Recipe
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goToast
import com.cookMeGood.makeItTasteIt.utils.IntentContainer
import com.cookMeGood.makeItTasteIt.view.custom.Backdrop
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.layout_backdrop_back.*
import kotlinx.android.synthetic.main.layout_backdrop_front.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class SearchActivity: SuperActivity() {

    private var caloriesValueFrom = 0
    private var caloriesValueTo = 2000
    private var timeValueFrom = 5
    private var timeValueTo = 120

    private var searchQueryText: String = ""
    private var searchContentList: List<Recipe> = listOf(
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"),
            Recipe(null, "Рецепт", "Описание рецепта Описание рецепта Описание рецепта Описание рецепта", null, null, null, "time", null, "Кухня"))
    private var filterCategoriesList = listOf("Супы", "Горячее", "Сладкое", "Напитки", "Закуски")
    private var filterKitchenList = listOf("Грузинская", "Китайская", "Русская", "Немецкая", "Абхазская")

    private var searchContentAdapter: SearchContentAdapter? = null
    private var filterCategoryAdapter: SearchFilterAdapter? = null
    private var filterKitchenAdapter: SearchFilterAdapter? = null

    private val openRecipeListener = object: OnOpenRecipeListener {
        override fun openRecipe(recipe: Recipe) {
            val intent = Intent(applicationContext, RecipeActivity::class.java)
            intent.putExtra(IntentContainer.INTENT_RECIPE, recipe)
            startActivity(intent)
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_search)

    @SuppressLint("SetTextI18n")
    override fun initInterface() {
        setSupportActionBar(startActivityToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

//        getAllCategoriesFromServer()

        searchContentAdapter = SearchContentAdapter(searchContentList, applicationContext, openRecipeListener)
        searchActivityContentList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        searchActivityContentList.adapter = searchContentAdapter

        filterCategoryAdapter = SearchFilterAdapter(filterCategoriesList, applicationContext)
        searchDishTypeList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        searchDishTypeList.adapter = filterCategoryAdapter

        filterKitchenAdapter = SearchFilterAdapter(filterKitchenList, applicationContext)
        searchKitchenList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        searchKitchenList.adapter = filterKitchenAdapter

        searchIngredients.setOnClickListener {

        }

        searchCaloriesRangeSlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter "${value.roundToInt()}Cal"
        }

        searchCaloriesRangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
            caloriesValueFrom = slider.values.first().toInt()
            caloriesValueTo = slider.values.last().toInt()

            if (caloriesValueFrom == caloriesValueTo){
                searchCaloriesValue.text = "$caloriesValueFrom"
            }
            else {
                searchCaloriesValue.text = "от $caloriesValueFrom до $caloriesValueTo"
            }
        })

        searchTimeRangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
            timeValueFrom = slider.values.first().toInt()
            timeValueTo = slider.values.last().toInt()

            if (timeValueFrom == timeValueTo){
                searchTimeValue.text = "$timeValueFrom минут"
            }
            else{
                searchTimeValue.text = "от $timeValueFrom до $timeValueTo минут"
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun hideFrontContent(hide: Boolean){
        if (hide){
            searchActivityContentList.visibility = View.GONE
            //TODO: показывать картинку-заглушку
        }
        else{
            searchActivityContentList.visibility = View.VISIBLE
        }
    }

    private fun getSearchListFromServer(searchText: String){
        //TODO: добавить параметр "Категории"
        //TODO: прикрутить API
    }

    private fun getAllCategoriesFromServer(){
        ApiService.getApi()
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {

                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }

}