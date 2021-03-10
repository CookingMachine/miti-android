package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestIngredientDialogListAdapter
import kotlinx.android.synthetic.main.item_suggest_edit_ingredient.view.*

class SuggestIngredientDialogAdapter(private var position: Int,var listener: SuggestIngredientEditListener): AppCompatDialogFragment() {
    private var suggestIngredientDialogListAdapter: SuggestIngredientDialogListAdapter? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater

        val view = inflater.inflate(R.layout.item_suggest_edit_ingredient, null)
        builder.setView(view).setPositiveButton("Изменить"){ _, _->
            listener.editIngredient(view.nameTextField.text.toString(),
                                    view.amountTextField.text.toString(),
                                    position)
        }
        builder.setView(view).setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}