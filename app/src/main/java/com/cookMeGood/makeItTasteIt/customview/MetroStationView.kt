package com.cookMeGood.makeItTasteIt.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.utils.ContextUtils

class MetroStationView : AppCompatTextView {

    var lineNumber = 0
        set(value) {
            field = value
            applyStationColor()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("Recycle")
    private fun init(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val arr = context.obtainStyledAttributes(attrs, R.styleable.MetroStationView, 0, 0)

            linkStationColor(arr.getString(R.styleable.MetroStationView_lineNumber) ?: "")
        }
    }

    private fun linkStationColor(stationNumber: String): Int {

        return when (stationNumber.toInt()) {
            1 -> ContextCompat.getColor(context, R.color.colorMetroStation1)
            2 -> ContextCompat.getColor(context, R.color.colorMetroStation2)
            3 -> ContextCompat.getColor(context, R.color.colorMetroStation3)
            4 -> ContextCompat.getColor(context, R.color.colorMetroStation4)
            5 -> ContextCompat.getColor(context, R.color.colorMetroStation5)
            6 -> ContextCompat.getColor(context, R.color.colorMetroStation6)
            7 -> ContextCompat.getColor(context, R.color.colorMetroStation7)
            8 -> ContextCompat.getColor(context, R.color.colorMetroStation8)
            9 -> ContextCompat.getColor(context, R.color.colorMetroStation9)
            10 -> ContextCompat.getColor(context, R.color.colorMetroStation10)
            11 -> ContextCompat.getColor(context, R.color.colorMetroStation11)
            12 -> ContextCompat.getColor(context, R.color.colorMetroStation12)
            else -> ContextCompat.getColor(context, R.color.colorWhite)
        }
    }

    private fun applyStationColor() {
        val viewDrawable = ContextCompat.getDrawable(context, R.drawable.ic_metro_station_circle)!!.mutate()
        DrawableCompat.setTint(viewDrawable, linkStationColor(lineNumber.toString()))
        setCompoundDrawablesWithIntrinsicBounds(viewDrawable, null, null, null)
        compoundDrawablePadding = ContextUtils.convertDpToPixel(6f, context).toInt()
    }
}