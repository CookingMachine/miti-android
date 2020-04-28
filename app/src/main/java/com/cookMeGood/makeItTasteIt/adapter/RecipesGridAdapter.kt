package com.cookMeGood.makeItTasteIt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.fragment.CategoryFragment
import com.cookMeGood.makeItTasteIt.listener.OnFragmentChangeListener
import kotlinx.android.synthetic.main.item_main_recipe.view.*

class RecipesGridAdapter(val context: Context, categoryList: List<String>,
                         private val changeListener: OnFragmentChangeListener):
        RecyclerView.Adapter<RecipesGridAdapter.ViewHolder>() {


    private var categoryList: List<String> = categoryList
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_main_recipe,
                        parent, false
                ))
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = this.categoryList[position]
        holder.amount.text = "101 позиция"
        holder.layout.layoutParams = setMargins(holder, position % 2 == 0)
        holder.layout.setOnClickListener {changeListener.replaceFragment(CategoryFragment())
        }
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val layout = item.gridRecipeLayout!!
        val name = item.recipeName!!
        val amount = item.recipeAmount!!
    }

    private fun setMargins(holder: ViewHolder, isSecond: Boolean): ViewGroup.LayoutParams? {
        val p = holder.layout.layoutParams as MarginLayoutParams
        if (isSecond) {
            p.setMargins(15, 20, 15, 0)
        } else {
            p.setMargins(15, 20, 15, 0)
        }
        return p
    }

    fun onUpdateList(newList: List<String>) {
        this.categoryList = newList
        notifyDataSetChanged()
    }
}