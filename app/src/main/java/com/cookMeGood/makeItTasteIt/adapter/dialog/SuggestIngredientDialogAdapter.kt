package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient

class SuggestIngredientDialogAdapter( val ingredientList : List<Ingredient>): AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.item_suggest_edit_ingredient_dialog,null)
        /*builder.setView(view).setPositiveButton("Ок"){

        }*/
        builder.setView(view).setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}