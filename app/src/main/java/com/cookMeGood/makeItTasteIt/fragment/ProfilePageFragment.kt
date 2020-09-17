package com.cookMeGood.makeItTasteIt.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.item_profile_dialog_screen.*


open class ProfilePageFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setCancelable(true)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.item_profile_dialog_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileCloseButton.setOnClickListener{ dialog!!.dismiss()}
        accountButton.setOnClickListener { goToast("Аккаунт") }
        ratingsButton.setOnClickListener { goToast("Оценки") }
        historyButton.setOnClickListener { goToast("Просмотренные") }
        settingsButton.setOnClickListener { goToast("Настройки") }
        contactButton.setOnClickListener { goToast("Написать разпаботчикам") }
        rateButton.setOnClickListener { goToast("Оценить") }
        aboutButton.setOnClickListener { goToast("О приложении") }

    }
    private fun goToast(output: String) {
        val errorToast = Toast.makeText(requireContext(), output, Toast.LENGTH_SHORT)
        errorToast.show()
    }
}