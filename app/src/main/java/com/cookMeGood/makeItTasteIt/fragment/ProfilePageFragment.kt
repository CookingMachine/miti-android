package com.cookMeGood.makeItTasteIt.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goShortToast
import kotlinx.android.synthetic.main.item_profile_dialog_screen.*

open class ProfilePageFragment: DialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dialog?.setCancelable(true)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.item_profile_dialog_screen, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileName.text = DataContainer.currentUser!!.name
        profileEmail.text = DataContainer.currentUser!!.email

        profileCardLayout.layoutParams.width =
                ContextUtils.getWindowWidth(requireActivity().windowManager) -
                ContextUtils.convertDpToPixel(64, requireContext())

        profileCloseButton.setOnClickListener{ dialog!!.dismiss()}
        ratingsButton.setOnClickListener { goShortToast(requireContext(),"Оценки") }
        historyButton.setOnClickListener { goShortToast(requireContext(),"Просмотренные") }
        settingsButton.setOnClickListener { goShortToast(requireContext(),"Настройки") }
        contactButton.setOnClickListener { goShortToast(requireContext(),"Написать разработчикам") }
        rateButton.setOnClickListener { goShortToast(requireContext(),"Оценить") }
        aboutButton.setOnClickListener { goShortToast(requireContext(),"О приложении") }
    }
}