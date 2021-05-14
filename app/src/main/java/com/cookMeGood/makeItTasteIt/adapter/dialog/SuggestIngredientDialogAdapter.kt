package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestIngredientEditListener
import kotlinx.android.synthetic.main.item_suggest_edit_ingredient.view.*

class SuggestIngredientDialogAdapter(private val action:String,
                                     private var position: Int,
                                     var listener: SuggestIngredientEditListener,
                                     var name:String? = null,
                                     private var amount: String? = null
) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater

        val view = inflater.inflate(R.layout.item_suggest_edit_ingredient, null)
        view.nameTextField.setText(name)
        view.amountTextField.setText(amount)
        builder.setView(view).setPositiveButton("Изменить"){ _, _->
            listener.editIngredient(view.nameTextField.text.toString(),
                                    view.amountTextField.text.toString(),
                                    position)
        }
        builder.setView(view).setNegativeButton("Отмена") { dialog, _ ->
            if(action=="AddIngredient") {
            listener.removeIngredient(position)
            }
            dialog.cancel() }

        return builder.create()
    }
}