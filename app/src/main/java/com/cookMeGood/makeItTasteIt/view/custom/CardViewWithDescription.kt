package com.cookMeGood.makeItTasteIt.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.item_card_view_with_description.view.*

class CardViewWithDescription: LinearLayout {

    var theme: String = ""
        set(value) {
            field = value
            cardViewTheme.text = field
        }

    var title: String = ""
        set(value) {
            field = value
            cardViewTitle.text = field
        }

    var description: String = ""
        set(value) {
            field = value
            cardViewDescription.text = field
        }

    var backgroundImage: Drawable? = null
        set(value) {
            field = value
            cardViewImage.setImageDrawable(field)
        }

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context){
        init(context, attrs)
    }


    @SuppressLint("Recycle")
    private fun init(context: Context, attrs: AttributeSet?){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_card_view_with_description, this)

        val arr = context.obtainStyledAttributes(attrs, R.styleable.CardViewWithDescription, 0, 0)

        theme = arr.getString(R.styleable.CardViewWithDescription_cardTheme) ?: ""
        title = arr.getString(R.styleable.CardViewWithDescription_cardTitle) ?: ""
        description = arr.getString(R.styleable.CardViewWithDescription_cardDescription) ?: ""
        backgroundImage = arr.getDrawable(R.styleable.CardViewWithDescription_cardImage)
    }

}