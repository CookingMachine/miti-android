package com.cookMeGood.makeItTasteIt.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.api.dto.*

object ContextUtils {

    fun goShortToast(context: Context, output: String) {
        Toast.makeText(context, output, Toast.LENGTH_SHORT).show()
    }

    fun goLongToast(context: Context, output: String) {
        Toast.makeText(context, output, Toast.LENGTH_LONG).show()
    }

    fun convertDpToPixel(dp: Float, context: Context): Float =
        dp * (context.resources.displayMetrics.densityDpi.toFloat() /
                DisplayMetrics.DENSITY_DEFAULT)

    fun convertDpToPixel(dp: Int, context: Context): Int {
        return convertDpToPixel(dp.toFloat(), context).toInt()
    }

    fun convertPixelsToDp(px: Float, context: Context): Float =
        px / (context.resources.displayMetrics.densityDpi.toFloat() /
                DisplayMetrics.DENSITY_DEFAULT)

    fun convertPixelsToDp(px: Int, context: Context): Int {
        return convertPixelsToDp(px.toFloat(), context).toInt()
    }

    fun getActionBarSize(context: Context): Int {
        val styledAttr = context
            .theme
            .obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return styledAttr.getDimension(0, 0f).toInt()
    }

    fun getStatusBarHeightInPixels(resources: Resources): Int {
        var result = 0
        val resourceId: Int = resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getWindowHeight(windowManager: WindowManager): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getWindowWidth(windowManager: WindowManager): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun convertSecondsToCorrectTime(secondToParse: String): String {
        val hours = secondToParse.toInt() / 3600
        val minutes = secondToParse.toInt() / 60 % 60
        return if (hours != 0) {
            if (minutes != 0) {
                "$hours ч $minutes мин"
            } else {
                "$hours ч"
            }
        } else {
            "$minutes мин"
        }
    }

    fun getStubRecipe(): Recipe = Recipe(
        1, "Пицца", "Для большой компании",
        null, null, "Национальная", User(), Category(),
        listOf(Comment()), arrayListOf(ContextIngredient()), CalorieContent(),
        listOf(User()), listOf(Rating()), ""
    )

    fun getStubRecipeList(): List<Recipe> = listOf(
        Recipe(
            1, "Пицца", "Для большой компании", null,
            null, "Национальная", User(), Category(),
            listOf(Comment()), arrayListOf(ContextIngredient()), CalorieContent(),
            listOf(User()), listOf(Rating()), ""
        ),
        Recipe(
            1, "Пицца", "Для большой компании", null,
            null, "Национальная", User(), Category(),
            listOf(Comment()), arrayListOf(ContextIngredient()), CalorieContent(),
            listOf(User()), listOf(Rating()), ""
        ),
        Recipe(
            1, "Пицца", "Для большой компании", null,
            null, "Национальная", User(), Category(),
            listOf(Comment()), arrayListOf(ContextIngredient()), CalorieContent(),
            listOf(User()), listOf(Rating()), ""
        )
    )

    fun getStubUser(): User =
        User(
            1, "username", "name", "pass", "email",
            true, null, null, null, null,
            null, null, null
        )

    fun getStubIngredientsList(): List<Ingredient> =
        listOf(
            Ingredient("Абрикос", "10"),
            Ingredient("Арбуз", "10"),
            Ingredient("Банан", "10"),
            Ingredient("Виноград", "10"),
            Ingredient("Горох", "10"),
            Ingredient("Огурец", "10"),
            Ingredient("Морковь", "10"),
            Ingredient("Катрофель", "10")
        )

    fun getStubCategoryList(): List<String> =
        listOf("Супы", "Горячее", "Сладкое", "Напитки", "Закуски")

    fun getStubKitchenList(): List<String> =
        listOf("Грузинская", "Китайская", "Русская", "Немецкая", "Абхазская")

    fun getStubRestaurants(): List<Restaurant> = listOf(
        Restaurant(
            0, "Золотая бухта", "Украинская",
            "Арбатская 7, Москва", 4.2,
            MetroStation(4, "Арбатская"), "1500",
            listOf("Wi-fi", "еда на вынос")
        ),
        Restaurant(
            0, "Золотая бухта", "Украинская",
            "Арбатская 7, Москва", 4.2,
            MetroStation(1, "Юго-западная"), "1500",
            listOf("Wi-fi", "еда на вынос")
        ),
        Restaurant(
            0, "Золотая бухта", "Украинская",
            "Арбатская 7, Москва", 4.2,
            MetroStation(4, "Арбатская"), "1500",
            listOf("Wi-fi", "еда на вынос")
        )
    )

    fun getStubContextIngredientList() = arrayListOf(
        ContextIngredient(
            0, 10, Measure.Kg, Ingredient("Мука", "1"), Recipe()
        )
    )
}
