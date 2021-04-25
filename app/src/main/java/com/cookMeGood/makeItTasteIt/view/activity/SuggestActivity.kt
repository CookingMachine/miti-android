package com.cookMeGood.makeItTasteIt.view.activity

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
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestEditFieldDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestIngredientListAdapter
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestStepListAdapter
import com.miti.api.model.Ingredient
import com.miti.api.model.Step
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_suggest.*
import kotlinx.android.synthetic.main.content_suggest_recipe_bottom_sheet.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SuggestActivity : SuperActivity() {


    private var suggestStepListAdapter: SuggestStepListAdapter? = null

    private var suggestIngredientListAdapter: SuggestIngredientListAdapter? = null

    private var suggestEditStepDialogDialog: SuggestEditFieldDialogAdapter? = null
    var ingredientList = arrayListOf(Ingredient("Ингедиент", "кол-во"))
    var stepList = arrayListOf(Step("Шаг", 1))
    private var suggestStepEditListener = object: SuggestStepEditListener {

        override fun editStep(title: String, position: Int, text: String) {
            when (title) {
                "Название" -> suggestActivityBottomSheetName.text = text
                "Описание" -> suggestActivityBottomSheetDescription.text = text
                "Описание шага" -> suggestStepListAdapter!!.onChangeStepDescription(position, text)
            }
        }
    }
    private var suggestIngredientEditListener = object: SuggestIngredientEditListener {
        override fun editIngredient(name: String, amount: String, position: Int) {
            suggestIngredientListAdapter!!.onChangeIngredient(position, name, amount)
            }

        override fun removeIngredient(position: Int) {
            suggestIngredientListAdapter!!.onRemoveIngredient(position)
        }
    }

    private lateinit var currentPhotoPath: String

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override fun initInterface() {

        val stepListClickAnimation = AnimationUtils.loadLayoutAnimation(applicationContext, R.anim.anim_layout_list_fall_down)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.BLACK
        }

        onButtonClick(suggestActivityRecipeButton)
        setRecyclerViewItemDragListener()
        setIngredientRecyclerViewItemDragListener()

        suggestStepListAdapter = SuggestStepListAdapter(applicationContext, supportFragmentManager,stepList, suggestStepEditListener)
        suggestActivityStepList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        suggestActivityStepList.layoutAnimation = stepListClickAnimation
        suggestActivityStepList.adapter =   suggestStepListAdapter

        suggestIngredientListAdapter = SuggestIngredientListAdapter(supportFragmentManager,ingredientList, suggestIngredientEditListener)
        suggestActivityIngredientList.layoutManager = LinearLayoutManager(this)
        //suggestActivityIngredientList.layoutAnimation = stepListClickAnimation
        suggestActivityIngredientList.adapter = suggestIngredientListAdapter

        suggestActivityBottomSheetName.setOnClickListener {
            suggestEditStepDialogDialog = SuggestEditFieldDialogAdapter("Название", 0, suggestStepEditListener)
            suggestEditStepDialogDialog!!.show(supportFragmentManager, "Title")
        }

        suggestActivityBottomSheetDescription.setOnClickListener {
            suggestEditStepDialogDialog = SuggestEditFieldDialogAdapter("Описание", 0, suggestStepEditListener)
            suggestEditStepDialogDialog!!.show(supportFragmentManager, "Description")
        }

        suggestActivityImage.setOnClickListener {
            openDialog()
        }

        suggestActivitySaveButton.setOnClickListener {
            HelpUtils.goShortToast(applicationContext, "SAVED") //TODO: сохранение предложенного рецепта
        }

        suggestActivityRecipeButton.setOnClickListener { onButtonClick(suggestActivityRecipeButton) }
        suggestActivityIngredientsButton.setOnClickListener { onButtonClick(suggestActivityIngredientsButton) }

    }

    private fun onButtonClick(view: View){
        if (view == suggestActivityRecipeButton!!){
            suggestActivityIngredientsButton.setBackgroundResource(R.drawable.rounded_corners_button)
            suggestActivityIngredientsButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            suggestActivityRecipeButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
            suggestActivityRecipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

            suggestActivityStepList.visibility = View.VISIBLE
            suggestActivityIngredientList.visibility = View.GONE
        }
        else {
            suggestActivityRecipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
            suggestActivityRecipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            suggestActivityIngredientsButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
            suggestActivityIngredientsButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

            suggestActivityStepList.visibility = View.GONE
            suggestActivityIngredientList.visibility = View.VISIBLE
        }
    }

    override fun setAttr() =  setLayout(R.layout.activity_suggest)

    private fun setRecyclerViewItemDragListener(){
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
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

    private fun setIngredientRecyclerViewItemDragListener(){
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
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
    private fun openDialog(){
        val items = arrayOf<CharSequence>("Сделать фото", "Выбрать из галлереи")
        val dialog = AlertDialog.Builder(this).setTitle("Выбор изображения").setItems(items
        ) { _, which ->
            if (which==0){ openCamera()}
            else {openGallery()}
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
                        val uriCamera = Uri.fromFile(filePath) //TODO:  Отправить сделанное фото

                        CropImage.activity(uriCamera)
                                .setAspectRatio(4, 3)
                                .start(this)
                    }
                    REQUEST_PICK_IMAGE -> {
                        val uriGallery = data?.data //TODO: Отправить юри на серв

                        CropImage.activity(uriGallery)
                                .setAspectRatio(4, 3)
                                .start(this)
                    }
                    CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE-> {
                        val res = CropImage.getActivityResult(data)
                        suggestActivityImage.setImageURI(res.uri)
                        suggestActivityAddImage.visibility = View.GONE
                    }
                }
            }
        }
    }
}