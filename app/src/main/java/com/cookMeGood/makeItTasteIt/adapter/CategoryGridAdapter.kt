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

class CategoryGridAdapter(val context: Context, categoryList: List<String>,
                          private val changeListener: OnFragmentChangeListener,
                          private val windowWidth: Int):
        RecyclerView.Adapter<CategoryGridAdapter.ViewHolder>() {


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

        holder.layoutFirst.layoutParams.width = windowWidth / 2 - 45
        holder.layoutSecond.layoutParams.width = windowWidth / 2 - 45

        holder.nameFirst.text = this.categoryList[position]
        holder.amountFirst.text = "101 позиция"

        holder.nameSecond.text = this.categoryList[position]
        holder.amountSecond.text = "101 позиция"

        holder.layout.setOnClickListener {
            changeListener.replaceFragment(CategoryFragment())
        }
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val layout = item.gridRecipeLayout!!

        val layoutFirst = item.categoryFirst!!
        val nameFirst = item.categoryNameFirst!!
        val amountFirst = item.categoryAmountFirst!!
        val imageFirst = item.categoryImageSecond!!

        val layoutSecond = item.categorySecond!!
        val nameSecond = item.categoryNameSecond!!
        val amountSecond = item.categoryAmountSecond!!
        val imageSecond = item.categoryImageSecond!!
    }

    fun onUpdateList(newList: List<String>) {
        this.categoryList = newList
        notifyDataSetChanged()
    }
}