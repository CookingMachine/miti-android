package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestIngredientDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient
import kotlinx.android.synthetic.main.item_suggest_ingredient.view.*

class SuggestIngredientListAdapter (val context : Context,
                                    private val supportFragmentManager : FragmentManager,
                                    val listener: SuggestIngredientEditListener):
        RecyclerView.Adapter<SuggestIngredientListAdapter.ViewHolder>(){

    private var suggestIngredientDialogAdapter: SuggestIngredientDialogAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestIngredientListAdapter.ViewHolder {
        return SuggestIngredientListAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_suggest_ingredient,
                        parent, false
                )
        )
    }

    private val ingredientList = arrayListOf<Ingredient>()

    override fun onBindViewHolder(holder: SuggestIngredientListAdapter.ViewHolder, position: Int) {
        holder.ingredientChangeButton.setOnClickListener {
            suggestIngredientDialogAdapter = SuggestIngredientDialogAdapter(ingredientList)
            suggestIngredientDialogAdapter!!.show(supportFragmentManager,"Ingredient")
        }
    }

    override fun getItemCount(): Int = ingredientList.size + 1

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ingredientTitle = view.suggestIngredientTitle!!
        val ingredientAmount = view.suggestIngredientAmount!!
        val ingredientChangeButton = view.suggestIngredientChangeImage!!
        val ingredientLayout = view.suggestIngredientLayout
        //val ingredientDivider = view.suggestRecipeDivider!!
    }

}