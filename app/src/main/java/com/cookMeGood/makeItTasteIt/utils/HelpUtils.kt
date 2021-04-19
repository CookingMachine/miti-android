package com.cookMeGood.makeItTasteIt.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.miti.api.model.*

object HelpUtils {

    fun goToast(context: Context, output: String) {
        Toast.makeText(context, output, Toast.LENGTH_SHORT).show()
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDpToPixel(dp: Int, context: Context): Int {
        return convertDpToPixel(dp.toFloat(), context).toInt()
    }
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Int, context: Context): Int {
        return convertPixelsToDp(px.toFloat(), context).toInt()
    }

    fun getActionBarSize(context: Context): Int {
        val styledAttr = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return styledAttr.getDimension(0, 0f).toInt()
    }

    fun getStatusBarHeightInPixels(resources: Resources): Int {
        var result = 0
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getWindowHeight(windowManager: WindowManager): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.height()
        }
        else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    fun getStubRecipeList(): List<Recipe> {
        return listOf(
                Recipe(1, "Пицца", "Для большой компании", null, null, "Национальная", User(), Category(), listOf(Comment()), ContextIngredient(), CalorieContent(), listOf(User()), listOf(Rating()), ""),
                Recipe(1, "Пицца", "Для большой компании", null, null, "Национальная", User(), Category(), listOf(Comment()), ContextIngredient(), CalorieContent(), listOf(User()), listOf(Rating()), "")
        )
    }

    fun getStubUser(): User {
        return User(1, "username", "name", "pass", "email", true, null, null, null, null, null, null, null)
    }
}