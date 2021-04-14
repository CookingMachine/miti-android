package com.cookMeGood.makeItTasteIt.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.item_card_view_with_description.view.*

class CardViewWithTitle: LinearLayout {

    var title: String = ""
        set(value) {
            field = value
            cardViewTitle.text = field
        }

    var backgroundImage: Drawable? = null
        set(value) {
            field = value
            cardViewImage.setImageDrawable(field)
        }

    constructor(context: Context): super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("CustomViewStyleable", "Recycle")
    private fun init(context: Context, attrs: AttributeSet?){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_card_view_with_title, this)

        val arr = context.obtainStyledAttributes(attrs, R.styleable.CardViewWithTitle, 0, 0)

        title = arr.getString(R.styleable.CardViewWithTitle_title) ?: ""
        backgroundImage = arr.getDrawable(R.styleable.CardViewWithTitle_image)
    }

}