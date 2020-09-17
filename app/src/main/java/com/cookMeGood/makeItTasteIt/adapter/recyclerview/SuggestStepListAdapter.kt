package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestEditFieldDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import com.cookMeGood.makeItTasteIt.dto.Step
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.item_suggest_step.view.*

class SuggestStepListAdapter(val context: Context,
                             private val supportFragmentManager: FragmentManager,
                             val listener: SuggestStepEditListener):
        RecyclerView.Adapter<SuggestStepListAdapter.ViewHolder>(){

    private var suggestEditStepDialogAdapter: SuggestEditFieldDialogAdapter? = null
    private var stepList = arrayListOf<Step>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_suggest_step,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = stepList.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (itemCount - 1 == position) {
            setLastElement(holder)
        }
        else {
            holder.stepNumber.text = (position + 1).toString()
            holder.stepTitle.text = "Шаг"
            holder.stepAddButton.visibility = View.GONE
            holder.stepChangeButton.visibility = View.VISIBLE

            holder.stepLayout.setOnClickListener {
                HelpUtils.goToast(context, "SHOW DESCRIPTION: ${stepList[position].description}")
            }
        }

        holder.stepChangeButton.setOnClickListener {
            suggestEditStepDialogAdapter = SuggestEditFieldDialogAdapter("Описание шага", position, listener)
            suggestEditStepDialogAdapter!!.show(supportFragmentManager, "Step Description")
        }
    }

    private fun setLastElement(holder: ViewHolder){

        holder.stepNumber.text = (itemCount).toString()
        holder.stepTitle.text = "Шаг"
        holder.stepAddButton.visibility = View.VISIBLE
        holder.stepChangeButton.visibility = View.GONE

        holder.stepAddButton.setOnClickListener {
            stepList.add(Step())
            notifyDataSetChanged()
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val stepTitle = view.suggestStepTitle!!
        val stepNumber = view.suggestStepNumber!!
        val stepAddButton = view.suggestStepAddButton!!
        val stepChangeButton = view.suggestStepChangeImage!!
        val stepLayout = view.suggestStepLayout!!
    }

    fun onRemoveStep(position: Int){
        stepList.removeAt(position)
        notifyDataSetChanged()
    }

    fun onChangeStepDescription(position: Int, text: String){
        stepList[position].description = text
        HelpUtils.goToast(context, "DATA: $text")
        notifyDataSetChanged()
    }
}