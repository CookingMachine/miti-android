package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.item_login_alert.view.*

class LogInDialogAdapter : AppCompatDialogFragment() {

    var listener: LoginDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.item_login_alert,null)

        builder.setView(view)
                .setPositiveButton("Войти") { _, _ ->
                    val login: String = view.loginEdit.text.toString()
                    val password: String = view.passwordEdit.text.toString()
                    listener!!.loginUser(login, password)
                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as LoginDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() +
                    "must implement LoginDialogListener")
        }
    }

    interface LoginDialogListener {
        fun loginUser(login: String, password: String)
    }
}