package com.cookMeGood.makeItTasteIt.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.container.LogContainer.SAVE_CONTENT_DELETE
import com.cookMeGood.makeItTasteIt.container.LogContainer.SAVE_CONTENT_SAVE
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Утилитарный класс для сохранения и выгрузки изображений из памяти устройства
 */
object SaveContentHelper {
    /**
     * Сохранение изображения во внутренней памяти устройства
     *
     * @param applicationContext контекст приложения
     * @param drawableId идентификатор ресурса
     * @param recipeId идентификатор привязанного к изображению рецепта
     * @return имя сохраненного изображения в формате 'recipeId_recipe.jpg'
     */
    fun saveRecipeImageToInternalStorage(
            applicationContext: Context, drawableId: Int, recipeId: Long
    ): Uri {
        val drawable = ContextCompat.getDrawable(applicationContext, drawableId)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val wrapper = ContextWrapper(applicationContext)
        val directory = wrapper.getDir("files", Context.MODE_PRIVATE)

        val file = File(directory, "${recipeId}_recipe.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()
            stream.close()

            Log.i(SAVE_CONTENT_SAVE, "Recipe image was saved successfully.")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(SAVE_CONTENT_SAVE, "Recipe image saving failed!")
        }
        return Uri.parse(file.absolutePath + "${recipeId}_recipe.jpg")
    }

    /**
     * Получение изображения рецепта из внутреннего хранилища по URI
     *
     * @param applicationContext контекст приложения
     * @param imageUri идентификатор рецепта
     * @return Drawable ресурс с изображением
     */
    fun getRecipeImageFromInternalStorage(applicationContext: Context, imageUri: Uri):
            Drawable {
        val contentWrapper = ContextWrapper(applicationContext)
        val directory = contentWrapper.getDir(imageUri.path, Context.MODE_PRIVATE)
        return Drawable.createFromPath(File(directory, "recipe.jpeg").toString())!!
    }

    /**
     * Удаление изображения рецепта из внутреннего хранилища по названию
     *
     * @param applicationContext контекст приложения
     * @param filename название изображения
     * @return Boolean результат удаления
     */
    fun deleteRecipeImageFromInternalStorage(applicationContext: Context, filename: String): Boolean {
        return if (applicationContext.deleteFile(filename)) {
            Log.i(SAVE_CONTENT_DELETE, "Recipe image was successfully deleted.")
            true
        }
        else {
            Log.i(SAVE_CONTENT_DELETE, "Recipe image deletion failed!")
            false
        }

    }
}