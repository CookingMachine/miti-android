package com.shveed.cookmegood.adapter


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.shveed.cookmegood.R
import kotlinx.android.synthetic.main.login_alert.view.*

class LogInDialogAdapter : AppCompatDialogFragment() {
    var listener: LoginDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.login_alert,null)
        builder.setView(view)
                .setPositiveButton("Войти") { dialog, which ->
                    val login: String = view.loginEdit.text.toString()
                    val password: String = view.passwordEdit.text.toString()
                    listener!!.loginUser(login, password)
                }
                .setNegativeButton("Отмена") { dialog, which -> dialog.cancel() }
        return builder.create()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as LogInDialogAdapter.LoginDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() +
                    "must implement LoginDialogListener")
        }
    }
    interface LoginDialogListener {
        fun loginUser(login: String, password: String)
    }
}