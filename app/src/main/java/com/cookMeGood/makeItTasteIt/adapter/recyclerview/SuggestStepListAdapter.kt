package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.SuggestEditFieldDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.SuggestStepEditListener
import com.api.model.Step
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.item_suggest_step.view.*

class SuggestStepListAdapter(val context: Context,
                             private val supportFragmentManager: FragmentManager,
                             private var stepList: ArrayList<Step>,
                             val listener: SuggestStepEditListener):
        RecyclerView.Adapter<SuggestStepListAdapter.ViewHolder>(){

    private var suggestEditStepDialogAdapter: SuggestEditFieldDialogAdapter? = null

    private var originalHeight : Int = -1
    private var expandedHeight : Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_suggest_step,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = stepList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.stepDescription.text = stepList[position].description
        holder.stepTitle.text = stepList[position].name
        holder.stepNumber.text = stepList[position].number.toString()

        if (itemCount  == position+1) {
            setLastElement(holder)
        }
        else {
            val currentStep = stepList[position]

            holder.stepAddButton.visibility = View.GONE
            holder.stepChangeButton.visibility = View.VISIBLE

            if (!currentStep.description.equals("Описание отсутствует")){
                holder.stepDivider.visibility = View.VISIBLE
                holder.stepLayout.doOnLayout {view ->
                    originalHeight = view.height
                    holder.stepDescription.visibility = View.VISIBLE

                    view.doOnPreDraw {
                        expandedHeight = originalHeight + HelpUtils.convertDpToPixel(holder.stepDescription.height, context)
                        holder.stepDescription.visibility = View.GONE
                    }
                }

                holder.stepLayout.setOnClickListener {
                    expand(holder, holder.stepDescription.visibility == View.GONE)
                }
            }
        }

        holder.stepChangeButton.setOnClickListener {
            suggestEditStepDialogAdapter = SuggestEditFieldDialogAdapter("Описание шага", position, listener)
            suggestEditStepDialogAdapter!!.show(supportFragmentManager, "Step Description")
        }
    }

    private fun setLastElement(holder: ViewHolder){
        holder.stepAddButton.visibility = View.VISIBLE
        holder.stepAddButton.setOnClickListener {
            stepList.add(Step("Шаг", itemCount + 1))
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val stepTitle = view.suggestStepTitle!!
        val stepDescription = view.suggestStepDescription!!
        val stepNumber = view.suggestStepNumber!!
        val stepAddButton = view.suggestStepAddButton!!
        val stepChangeButton = view.suggestStepChangeImage!!
        val stepLayout = view.suggestStepLayout!!
        val stepDivider = view.suggestRecipeDivider!!
    }

    fun onRemoveStep(position: Int){
        stepList.removeAt(position)
        notifyDataSetChanged()
    }

    fun onChangeStepDescription(position: Int, text: String){
        stepList[position].description = text
        HelpUtils.goShortToast(context, "DATA: $text")
        notifyDataSetChanged()
    }

    private inline fun getValueAnimator(forward: Boolean = true,
                                        duration: Long, interpolator: TimeInterpolator,
                                        crossinline updateListener:
                                        (progress: Float) -> Unit): ValueAnimator {
        val a =
                if (forward) ValueAnimator.ofFloat(0f, 1f)
                else ValueAnimator.ofFloat(1f, 0f)
        a.addUpdateListener { updateListener(it.animatedValue as Float) }
        a.duration = duration
        a.interpolator = interpolator
        return a
    }

    private fun expand(holder: ViewHolder, expand: Boolean) {
        val animator = getValueAnimator(
                expand, 200, AccelerateDecelerateInterpolator()
        ) { progress ->
            holder.stepLayout.layoutParams.height = (originalHeight + (expandedHeight - originalHeight - 30) * progress).toInt()
            holder.stepLayout.requestLayout()

            holder.stepDivider.translationY = (expandedHeight - originalHeight) * progress
            holder.stepDivider.rotation = 180 * progress
        }

        if (expand) animator.doOnStart {
            holder.stepDescription.visibility = View.VISIBLE
        }
        else animator.doOnEnd {
            holder.stepDescription.visibility = View.GONE
        }

        animator.start()
    }
}