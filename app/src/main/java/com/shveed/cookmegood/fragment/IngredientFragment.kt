package com.shveed.cookmegood.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shveed.cookmegood.adapter.IngredientsGridAdapter
import com.shveed.cookmegood.data.dto.Ingredient
import com.shveed.wallpapperparser.R
import java.util.*


class IngredientFragment : Fragment() {
    private var ingredients = arrayListOf<Ingredient>()
    private val data = arrayOf("Помидоры", "Салат", "Хлеб", "Майонез", "Чеснок", "Сыр", "Укроп", "Лук")
    private val amount = arrayOf("400г", "200г", "1 буханка", "200г", "2 головки", "300г", "50г", "50г")
    private val buyMap = HashMap<Ingredient, Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        for (i in 0..7) {
            val ingredient = Ingredient(data[i], amount[i])
            ingredients.add(ingredient)
        }
        val view = inflater.inflate(R.layout.f_ingredient, container, false)
        val recyclerView = view.findViewById<View>(R.id.gridIngred) as RecyclerView
        val adapter = IngredientsGridAdapter(activity, ingredients)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter
        return view
    }
    fun getBuyMap(): HashMap<Ingredient, Int>? {
        return buyMap
    }
}