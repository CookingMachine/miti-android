package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.api.ApiService
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnUpdateFavouritesListener
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goShortToast
import kotlinx.android.synthetic.main.content_cart_dialog.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesRemoveDialogAdapter(
    private val userId: Long,
    private val recipe: Recipe,
    val listener: OnUpdateFavouritesListener
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCancelable(true)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.content_cart_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartDialogRecipeName.text = recipe.name

        cartDialogButtonNo.setOnClickListener { dialog!!.dismiss() }
        cartDialogButtonYes.setOnClickListener {
            deleteFavouriteRecipeOnServer()
        }
    }

    private fun deleteFavouriteRecipeOnServer() {
        ApiService.getApi().deleteFavouriteRecipe(userId, recipe.id!!)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    listener.update(recipe)
                    dialog!!.dismiss()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    goShortToast(context!!, t.message.toString())
                    dialog!!.dismiss()
                }
            })
    }
}