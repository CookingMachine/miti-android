package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestIngredientDialogListAdapter
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient
import kotlinx.android.synthetic.main.item_suggest_edit_ingredient_dialog.view.*

class SuggestIngredientDialogAdapter(val ingredientList: ArrayList<Ingredient>): AppCompatDialogFragment() {
    private var suggestIngredientDialogListAdapter: SuggestIngredientDialogListAdapter? = null
    lateinit var customView: View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater

        val view = inflater.inflate(R.layout.item_suggest_edit_ingredient_dialog, null)
        suggestIngredientDialogListAdapter = SuggestIngredientDialogListAdapter(requireContext(), requireFragmentManager(), ingredientList)
        view.suggestIngredientDialogList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.suggestIngredientDialogList.adapter = suggestIngredientDialogListAdapter
        /*builder.setView(view).setPositiveButton("Ок"){

        }*/
        builder.setView(view).setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }

        return builder.create()
    }


    override fun onResume() {
        super.onResume()

    }
}