package com.cookMeGood.makeItTasteIt.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.fragment.CategoryFragment
import com.cookMeGood.makeItTasteIt.listener.OnFragmentChangeListener
import kotlinx.android.synthetic.main.item_main_recipe.view.*

class CategoryGridAdapter(val context: Context):
        RecyclerView.Adapter<CategoryGridAdapter.ViewHolder>() {

    private var items: MutableList<Pair<String, String?>> = mutableListOf()
    private lateinit var listener : OnFragmentChangeListener
    private var windowWidth: Int = 0

    constructor(context: Context, items: List<String>, changeListener: OnFragmentChangeListener,
                windowWidth: Int) : this(context) {
        this.listener = changeListener
        this.windowWidth = windowWidth
        setItems(items)
    }

    private fun setItems(items: List<String>) {
        if(this.items.isNotEmpty()){
            this.items.clear()
        }
        for (i in items.indices step 2) {
            val pair = Pair(
                    items[i],
                    if (i + 1 < items.size )
                        items[i + 1]
                    else
                        null
            )
            this.items.add(pair)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_main_recipe,
                        parent, false
                ))
    }

    override fun getItemCount(): Int = items.size

    private fun fillView(holder: ViewHolder, pair: Pair<String, String?>, first: Boolean) {
        if(!first && pair.second == null){
            holder.layoutSecond.visibility = View.GONE
            return
        }

        val item = if (first) pair.first else pair.second!!

        if(first){
            holder.layoutFirst.layoutParams.width = windowWidth / 2 - 45
            holder.nameFirst.text = item
            holder.amountFirst.text = "101 позиция"
            holder.imageFirst.setBackgroundResource(R.drawable.pic2)
        } else{
            holder.layoutSecond.layoutParams.width = windowWidth / 2 - 45
            holder.nameSecond.text = item
            holder.amountSecond.text = "101 позиция"
            holder.imageSecond.setBackgroundResource(R.drawable.pic3)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        fillView(holder, item, true)
        fillView(holder, item, false)

        holder.layoutFirst.setOnClickListener {
            listener.replaceFragment(CategoryFragment())
        }

        holder.layoutSecond.setOnClickListener {
            listener.replaceFragment(CategoryFragment())
        }
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val layout = item.gridRecipeLayout!!

        val layoutFirst = item.categoryFirst!!
        val nameFirst = item.categoryNameFirst!!
        val amountFirst = item.categoryAmountFirst!!
        val imageFirst = item.categoryImageFirst!!

        val layoutSecond = item.categorySecond!!
        val nameSecond = item.categoryNameSecond!!
        val amountSecond = item.categoryAmountSecond!!
        val imageSecond = item.categoryImageSecond!!
    }

    fun onUpdateList(newList: List<String>) {
        setItems(newList)
        notifyDataSetChanged()
    }
}