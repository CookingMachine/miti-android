package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestIngredientDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import com.miti.api.model.Ingredient
import kotlinx.android.synthetic.main.item_suggest_ingredient.view.*

class SuggestIngredientListAdapter(private val supportFragmentManager: FragmentManager,
                                   var ingredientList: ArrayList<Ingredient>,
                                   val listener: SuggestIngredientEditListener) :
        RecyclerView.Adapter<SuggestIngredientListAdapter.ViewHolder>() {

    private var suggestIngredientDialogAdapter: SuggestIngredientDialogAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_suggest_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ingredientTitle.text = ingredientList[position].name
        holder.ingredientAmount.text = ingredientList[position].amount

        if (itemCount == position + 1) {
            setLastElement(holder, position)
        } else {
            holder.ingredientAddButton.visibility = View.GONE
        }
        holder.ingredientChangeButton.setOnClickListener {
            suggestIngredientDialogAdapter = SuggestIngredientDialogAdapter(
                    "ChangeIngredient",
                    position,
                    listener,
                    ingredientList[position].name,
                    ingredientList[position].amount
            )
            suggestIngredientDialogAdapter!!.show(supportFragmentManager, "Edit Ingredient")
        }
    }

    override fun getItemCount(): Int = ingredientList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientTitle = view.suggestIngredientTitle!!
        val ingredientAmount = view.suggestIngredientAmount!!
        val ingredientChangeButton = view.suggestIngredientChangeImage!!
        val ingredientLayout = view.suggestIngredientLayout!!
        val ingredientAddButton = view.suggestIngredientAddButton!!
    }

    private fun setLastElement(holder: ViewHolder, position: Int) {
        holder.ingredientAddButton.visibility = View.VISIBLE
        holder.ingredientAddButton.setOnClickListener {
            ingredientList.add(Ingredient())
            suggestIngredientDialogAdapter = SuggestIngredientDialogAdapter("AddIngredient", position + 1, listener)
            suggestIngredientDialogAdapter!!.show(supportFragmentManager, "Add Ingredient")
        }
    }

    fun onChangeIngredient(position: Int, name: String, amount: String) {
        ingredientList[position].name = name
        ingredientList[position].amount = amount
        notifyDataSetChanged()
    }

    fun onRemoveIngredient(position: Int) {
        ingredientList.removeAt(position)
        notifyDataSetChanged()
    }


}