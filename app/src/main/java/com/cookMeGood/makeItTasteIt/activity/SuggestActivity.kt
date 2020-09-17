package com.cookMeGood.makeItTasteIt.activity

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestEditFieldDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestStepListAdapter
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.activity_suggest.*
import kotlinx.android.synthetic.main.layout_suggest_recipe_bottom_sheet.*

class SuggestActivity : SuperActivity() {

    private var suggestStepListAdapter: SuggestStepListAdapter? = null

    private var suggestEditStepDialogDialog: SuggestEditFieldDialogAdapter? = null

    private var suggestStepEditListener = object: SuggestStepEditListener{

        override fun editStep(title: String, position: Int, text: String) {
            when (title){
                "Название" -> suggestActivityBottomSheetName.text = text
                "Описание" -> suggestActivityBottomSheetDescription.text = text
                "Описание шага" -> suggestStepListAdapter!!.onChangeStepDescription(position, text)
            }
        }
    }

    override fun initInterface() {

        suggestStepListAdapter = SuggestStepListAdapter(applicationContext, supportFragmentManager, suggestStepEditListener )
        suggestActivityStepList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        suggestActivityStepList.adapter = suggestStepListAdapter

        onButtonClick(suggestActivityRecipeButton)
        setRecyclerViewItemDragListener()

        suggestActivityBottomSheetName.setOnClickListener {
            suggestEditStepDialogDialog = SuggestEditFieldDialogAdapter("Название", 0, suggestStepEditListener)
            suggestEditStepDialogDialog!!.show(supportFragmentManager, "Title")
        }

        suggestActivityBottomSheetDescription.setOnClickListener {
            suggestEditStepDialogDialog = SuggestEditFieldDialogAdapter("Описание", 0, suggestStepEditListener)
            suggestEditStepDialogDialog!!.show(supportFragmentManager, "Description")
        }

        suggestActivityImage.setOnClickListener {
            HelpUtils.goToast(applicationContext, "SELECT PICTURE WINDOW") //TODO: выбор картинки для рецепта
        }

        suggestActivitySaveButton.setOnClickListener {
            HelpUtils.goToast(applicationContext, "SAVED") //TODO: сохранение предложенного рецепта


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
        }
        else {
            suggestActivityRecipeButton.setBackgroundResource(R.drawable.rounded_corners_button)
            suggestActivityRecipeButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
            suggestActivityIngredientsButton.setBackgroundResource(R.drawable.shape_round_button_pressed)
            suggestActivityIngredientsButton.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))

            suggestActivityStepList.visibility = View.GONE
        }
    }

    override fun setAttr() {
        setLayout(R.layout.activity_suggest)
    }

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

    private fun checkRecipeFilling(){

    }
}