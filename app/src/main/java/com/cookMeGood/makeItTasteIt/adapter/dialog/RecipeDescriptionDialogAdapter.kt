package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.content_recipe_description.*

class RecipeDescriptionDialogAdapter(val description: String) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setCancelable(true)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.content_recipe_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeDescriptionDialogText.text = description
        recipeDescriptionDialogCloseButton.setOnClickListener {
            dialog!!.dismiss()
        }
    }

}