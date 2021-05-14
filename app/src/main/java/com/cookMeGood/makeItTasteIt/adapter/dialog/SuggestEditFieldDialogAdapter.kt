package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import kotlinx.android.synthetic.main.item_suggest_edit_description.*
import kotlinx.android.synthetic.main.item_suggest_edit_description.view.*
import kotlinx.android.synthetic.main.item_suggest_edit_step.*
import kotlinx.android.synthetic.main.item_suggest_edit_step.view.*
import kotlinx.android.synthetic.main.item_suggest_edit_title.*
import kotlinx.android.synthetic.main.item_suggest_edit_title.view.*
import java.lang.ClassCastException

class SuggestEditFieldDialogAdapter(val title: String,
                                    private val position: Int,
                                    var listener: SuggestStepEditListener
) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(
                when (title) {
                    "Название" -> R.layout.item_suggest_edit_title
                    "Описание" -> R.layout.item_suggest_edit_description
                    else -> R.layout.item_suggest_edit_step
                },
                null)

        builder.setView(view)
                .setPositiveButton("Изменить") { _, _ ->

                    when (title) {
                        "Название" -> {
                            listener.editStep(
                                    title, position, view.suggestTitleEditText.text.toString())
                        }
                        "Описание" -> {
                            listener.editStep(
                                    title, position, view.suggestDescriptionEditText.text.toString())
                        }
                        "Описание шага" -> {
                            listener.editStep(
                                    title, position, view.suggestStepEditText.text.toString())
                        }
                    }

                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }



        return builder.create()
    }
}