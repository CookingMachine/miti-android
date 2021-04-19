package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import com.miti.api.model.Category
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.view.fragment.CategoryFragment
import kotlinx.android.synthetic.main.item_main_category.view.*

class CategoryListAdapter(private var categories: List<Category>?,
                          var changeListener: OnFragmentChangeListener) :
        RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    private lateinit var listener: OnFragmentChangeListener
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        if (position < 1) {
            val margin8 = HelpUtils.convertDpToPixel(8, context)
            val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(margin8, 0, 0, 0)
            holder.categoryLayout.layoutParams = params
        }
        listener = changeListener
        holder.categoryName.text = categories!![position].name
        holder.categoryName.isSelected = true
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
        val categoryLayout = view.categoryLayout!!
    }
}
