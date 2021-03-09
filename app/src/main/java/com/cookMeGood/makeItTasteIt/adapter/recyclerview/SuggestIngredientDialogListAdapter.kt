package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestIngredientDialogAdapter
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient
import kotlinx.android.synthetic.main.item_suggest_edit_ingredient.view.*

class SuggestIngredientDialogListAdapter (val context : Context,
                                          private val supportFragmentManager: FragmentManager,
                                          val ingredientList : ArrayList<Ingredient>):
        RecyclerView.Adapter<SuggestIngredientDialogListAdapter.ViewHolder>(){

    private var suggestIngredientDialogAdapter: SuggestIngredientDialogAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestIngredientDialogListAdapter.ViewHolder {
        return SuggestIngredientDialogListAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_suggest_edit_ingredient,
                        parent, false
                )
        )
    }



    override fun onBindViewHolder(holder: SuggestIngredientDialogListAdapter.ViewHolder, position: Int) {
        /*holder.ingredientChangeButton.setOnClickListener {
            suggestIngredientDialogAdapter = SuggestIngredientDialogAdapter(ingredientList)
            suggestIngredientDialogAdapter!!.show(supportFragmentManager,"Ingredient")
        }*/
    }

    override fun getItemCount(): Int = ingredientList.size + 1

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ingredientAddButton = view.suggestIngredientAddButton
        val ingredientName = view.nameTextField
        val ingredientAmount = view.amountTextField
        val ingredientDeleteButton = view.suggestIngredientDeleteButton
    }

    fun onRemoveIngredient(position: Int){
        ingredientList.removeAt(position)
        notifyDataSetChanged()
    }

}