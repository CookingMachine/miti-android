package com.cookMeGood.makeItTasteIt.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.ApiService
import com.api.dto.Ingredient
import com.api.dto.Recipe
import com.api.dto.Step
import com.api.dto.request.RecipeAdditionRequest
import com.canhub.cropper.CropImage
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestEditFieldDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestIngredientListAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestStepListAdapter
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goShortToast
import kotlinx.android.synthetic.main.activity_suggest.suggestActivityAddImage
import kotlinx.android.synthetic.main.activity_suggest.suggestActivityImage
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityBottomSheetDescription
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityBottomSheetName
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityIngredientList
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityIngredientsButton
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityRecipeButton
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivitySaveButton
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityStepList
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.suggestActivityTimePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

class SuggestActivity : SuperActivity() {

    companion object {
        private const val REQUEST_PICK_IMAGE = 2
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val TIME_PICKER_REGEX = "^[0-9]:[0-5][0-9]\$"
    }

    private lateinit var currentPhotoPath: String

    private var suggestStepListAdapter: SuggestStepListAdapter? = null
    private var suggestIngredientListAdapter: SuggestIngredientListAdapter? = null
    private var suggestEditStepDialogAdapter: SuggestEditFieldDialogAdapter? = null

    private var currentRecipe: RecipeAdditionRequest = RecipeAdditionRequest()
    private var ingredientList = arrayListOf(Ingredient("Ингредиент", "Кол-во"))
    private var stepList = arrayListOf(Step(1, "Описание"))
    private var imageResource: CropImage.ActivityResult? = null

    private var suggestStepEditListener = object : SuggestStepEditListener {

        override fun editStep(title: String, position: Int, text: String) {
            when (title) {
                "Название" -> {
                    suggestActivityBottomSheetName.text = text
                    currentRecipe.name = text
                }
                "Описание" -> {
                    suggestActivityBottomSheetDescription.text = text
                    currentRecipe.description = text
                }
                "Описание шага" -> {
                    currentRecipe.steps!!.add(
                        suggestStepListAdapter!!.onChangeStepDescription(position, text)
                    )
                }
            }
        }
    }
    private var suggestIngredientEditListener = object : SuggestIngredientEditListener {
        override fun editIngredient(name: String, amount: String, position: Int) {
            suggestIngredientListAdapter!!.onChangeIngredient(position, name, amount)
        }

        override fun removeIngredient(position: Int) {
            suggestIngredientListAdapter!!.onRemoveIngredient(position)
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_suggest)

    override fun initInterface() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.BLACK
        }

        onButtonClick(suggestActivityRecipeButton)
        setStepRecyclerViewItemDragListener()
        setIngredientRecyclerViewItemDragListener()
        initAdapters()

        suggestActivityBottomSheetName.setOnClickListener {
            suggestEditStepDialogAdapter = SuggestEditFieldDialogAdapter(
                "Название", 0, suggestStepEditListener
            )
            suggestEditStepDialogAdapter!!.show(supportFragmentManager, "Title")
        }

        suggestActivityBottomSheetDescription.setOnClickListener {
            suggestEditStepDialogAdapter = SuggestEditFieldDialogAdapter(
                "Описание", 0, suggestStepEditListener
            )
            suggestEditStepDialogAdapter!!.show(supportFragmentManager, "Description")
        }

        suggestActivityImage.setOnClickListener {
            openImageSelectionDialog()
        }

        suggestActivitySaveButton.setOnClickListener {
            currentRecipe.time = suggestActivityTimePicker.text.toString()
            if (isRecipeFilledUpOrShowToast(currentRecipe)) {
                sendRecipeAndImageToServer(currentRecipe)
            }
        }

        suggestActivityRecipeButton.setOnClickListener {
            onButtonClick(suggestActivityRecipeButton)
        }
        suggestActivityIngredientsButton.setOnClickListener {
            onButtonClick(suggestActivityIngredientsButton)
        }
    }

    private fun initAdapters() {
        val stepListClickAnimation = AnimationUtils.loadLayoutAnimation(
            applicationContext, R.anim.anim_layout_list_fall_down
        )

        suggestStepListAdapter = SuggestStepListAdapter(
            applicationContext, supportFragmentManager, stepList, suggestStepEditListener
        )
        suggestActivityStepList.layoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.VERTICAL, false
        )
        suggestActivityStepList.layoutAnimation = stepListClickAnimation
        suggestActivityStepList.adapter = suggestStepListAdapter

        suggestIngredientListAdapter = SuggestIngredientListAdapter(
            supportFragmentManager, ingredientList, suggestIngredientEditListener
        )
        suggestActivityIngredientList.layoutManager = LinearLayoutManager(this)
        // suggestActivityIngredientList.layoutAnimation = stepListClickAnimation
        suggestActivityIngredientList.adapter = suggestIngredientListAdapter
    }

    private fun onButtonClick(view: View) {
        if (view == suggestActivityRecipeButton!!) {
            suggestActivityIngredientsButton
                .setBackgroundResource(R.drawable.shape_button_rounded_white)
            suggestActivityIngredientsButton
                .setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            suggestActivityRecipeButton
                .setBackgroundResource(R.drawable.shape_round_button_pressed)
            suggestActivityRecipeButton
                .setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

            suggestActivityStepList.visibility = View.VISIBLE
            suggestActivityIngredientList.visibility = View.GONE
        } else {
            suggestActivityRecipeButton
                .setBackgroundResource(R.drawable.shape_button_rounded_white)
            suggestActivityRecipeButton
                .setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            suggestActivityIngredientsButton
                .setBackgroundResource(R.drawable.shape_round_button_pressed)
            suggestActivityIngredientsButton
                .setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

            suggestActivityStepList.visibility = View.GONE
            suggestActivityIngredientList.visibility = View.VISIBLE
        }
    }

    private fun setStepRecyclerViewItemDragListener() {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    suggestStepListAdapter!!.onRemoveStep(position)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(suggestActivityStepList)
    }

    private fun setIngredientRecyclerViewItemDragListener() {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    suggestIngredientListAdapter!!.onRemoveIngredient(position)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(suggestActivityIngredientList)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }

    private fun openImageSelectionDialog() {
        val items = arrayOf<CharSequence>("Сделать фото", "Выбрать из галлереи")
        val dialog = AlertDialog.Builder(this).setTitle("Выбор изображения").setItems(
            items
        ) { _, which ->
            if (which == 0) {
                openCamera()
            } else {
                openGallery()
            }
        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                when (requestCode) {
                    REQUEST_IMAGE_CAPTURE -> {
                        val filePath = File(currentPhotoPath)
                        val uriCamera = Uri.fromFile(filePath)

                        CropImage.activity(uriCamera)
                            .setAspectRatio(4, 3)
                            .start(this)
                    }
                    REQUEST_PICK_IMAGE -> {
                        val uriGallery = data?.data

                        CropImage.activity(uriGallery)
                            .setAspectRatio(4, 3)
                            .start(this)
                    }
                    CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                        imageResource = CropImage.getActivityResult(data)
                        suggestActivityImage.setImageURI(imageResource!!.originalUri)
                        suggestActivityAddImage.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun sendRecipeAndImageToServer(recipe: RecipeAdditionRequest) {
        ApiService.getApi()
            .addRecipe(recipe)
            .enqueue(object : Callback<Recipe> {
                override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                    goShortToast(applicationContext, "Ваш рецепт отправлен на проверку!")
                    finish()
                }

                override fun onFailure(call: Call<Recipe>, t: Throwable) {
                    ContextUtils.goLongToast(applicationContext, t.message.toString())
                }
            })
    }

    private fun isRecipeFilledUpOrShowToast(recipe: RecipeAdditionRequest): Boolean {
        if (recipe.name.isNullOrEmpty()) {
            goShortToast(applicationContext, "Поле 'Наименование' не заполнено!")
            return false
        }
        if (recipe.time.isNullOrEmpty()) {
            goShortToast(applicationContext, "Поле 'Время приготовления' не заполнено!")
            return false
        }
        if (!isTimePickerInputCorrect(currentRecipe.time ?: "")) {
            goShortToast(applicationContext, "Неправильно заполнено время!")
            return false
        }
        if (recipe.description.isNullOrEmpty()) {
            goShortToast(applicationContext, "Поле 'Описание' не заполнено!")
            return false
        }
        if (imageResource == null) {
            goShortToast(applicationContext, "Картинка рецепта не выбрана!")
            return false
        }
//        if (recipe.contextIngredientList.isNullOrEmpty()) {
//            goShortToast(
//                applicationContext,
//                "Список ингредиентов не заполнен."
//            )
//            return false
//        } else {
//            if (recipe.contextIngredientList!!.size < 4) {
//                goShortToast(
//                    applicationContext,
//                    "Список ингредиентов имеет менее 4-х позиций."
//                )
//                return false
//            }
//        }
        return true
    }

    private fun isTimePickerInputCorrect(value: String): Boolean {
        return Pattern.matches(TIME_PICKER_REGEX, value)
    }
}
