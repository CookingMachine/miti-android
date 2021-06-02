package com.cookMeGood.makeItTasteIt.adapter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cookMeGood.makeItTasteIt.App
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnUpdateCartListListener
import com.database.model.RecipeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_cart_dialog.*

class CartRemoveDialogAdapter(
    private val recipe: RecipeModel,
    val listListenerCart: OnUpdateCartListListener
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
            App.instance.getDataBase().recipeDao().delete(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(t: Int) {
                        listListenerCart.update(recipe)
                        dialog!!.dismiss()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        dialog!!.dismiss()
                    }
                })
        }
    }

}