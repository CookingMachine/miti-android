package com.cookMeGood.makeItTasteIt.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class SlideAnimation(private val mView: View, private val mFromHeight: Int, private val mToHeight: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation?) {

        val newHeight: Int

        if (mView.height != mToHeight) {
            newHeight = (mFromHeight + (mToHeight - mFromHeight) * interpolatedTime).toInt()
            mView.layoutParams.height = newHeight
            mView.requestLayout()
        }
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}