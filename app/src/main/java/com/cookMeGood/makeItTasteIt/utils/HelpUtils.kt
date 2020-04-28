package com.cookMeGood.makeItTasteIt.utils

import android.content.Context
import android.widget.Toast

object HelpUtils {

    fun goToast(context: Context, output: String) {
        Toast.makeText(context, output, Toast.LENGTH_SHORT).show()
    }

}