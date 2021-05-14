package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFilterClickListener
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import kotlinx.android.synthetic.main.item_search_background_filter.view.*

class SearchFilterAdapter(private val items: List<String>,
                          private val onClickListener: OnFilterClickListener) :
        RecyclerView.Adapter<SearchFilterAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var selectedItemPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(
                                R.layout.item_search_background_filter,
                                parent, false
                        )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (position < 1) {
            val margin2 = ContextUtils.convertDpToPixel(2, context)
            val margin8 = ContextUtils.convertDpToPixel(8, context)
            val margin16 = ContextUtils.convertDpToPixel(16, context)
            val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    ContextUtils.convertDpToPixel(32, context)
            )
            params.setMargins(margin16, margin2, margin8, margin2)
            holder.filterLayout.layoutParams = params
        }
        holder.filterText.text = item

        if (position == selectedItemPosition) {
            holder.filterLayout.setBackgroundResource(R.drawable.shape_button_rounded_white)
            holder.filterText.setTextColor(ContextCompat.getColor(context, R.color.primaryColor))
        }
        else {
            holder.filterLayout.setBackgroundResource(R.drawable.shape_search_filter)
            holder.filterText.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        holder.filterLayout.setOnClickListener {
            selectedItemPosition = if (position == selectedItemPosition) {
                notifyDataSetChanged()
                onClickListener.onTouch("")
                null
            } else {
                notifyDataSetChanged()
                onClickListener.onTouch(item)
                position
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val filterText = view.filterText!!
        val filterLayout = view.filterLayout!!
    }
}