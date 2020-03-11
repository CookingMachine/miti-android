package com.shveed.cookmegood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.shveed.cookmegood.adapter.RecipeStepAdapter
import com.shveed.cookmegood.data.dto.Step
import com.shveed.wallpapperparser.R
import java.util.*

class RecipeFragment : Fragment() {
    var steps: MutableList<Step>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        steps = ArrayList()
        val view = inflater.inflate(R.layout.f_recipe, container, false)
        setData()
        val recyclerView = view.findViewById<View>(R.id.recipeStepRecycler) as RecyclerView
        val adapter = RecipeStepAdapter(activity, steps)
        recyclerView.adapter = adapter
        return view
    }

    fun setData() {
        for (i in 1..4) {
            steps!!.add(Step(i,
                    "Неторопясь нарезаем вкусненькую отваренную курочку",
                    "Нарезаем курицу"))
        }
    }
}