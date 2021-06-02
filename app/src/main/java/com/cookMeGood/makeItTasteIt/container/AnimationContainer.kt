package com.cookMeGood.makeItTasteIt.container

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.cookMeGood.makeItTasteIt.R

class AnimationContainer(context: Context) {

    val ANIM_FALL_DOWN: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_fall_down)
    val ANIM_SWIPE_RIGHT: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_swipe_right)

}
