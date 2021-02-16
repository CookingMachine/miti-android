package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.dto.Category
import com.cookMeGood.makeItTasteIt.view.fragment.CategoryFragment
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import kotlinx.android.synthetic.main.item_main_recipe.view.*

class CategoryListAdapter(private var categories: List<Category>?,
                          var changeListener: OnFragmentChangeListener) :
        RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    private lateinit var listener: OnFragmentChangeListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listener = changeListener
        holder.categoryName.text = categories!![position].name
        holder.categoryImage.setBackgroundResource(R.drawable.image_category1)

        holder.categoryImage.setOnClickListener {
            listener.replaceFragment(CategoryFragment(), categories!![position])
        }
    }

    override fun getItemCount(): Int = categories!!.size

    fun onUpdateList(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName = view.categoryName!!
        val categoryImage = view.categoryImage!!
    }
}
