package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RestaurantListAdapter
import com.api.dto.MetroStation
import com.api.dto.Restaurant
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_restaurant.*

class RestaurantFragment : SuperFragment() {

    private var restaurantsList: List<Restaurant> = listOf(
            Restaurant(0, "Золотая бухта", "Украинская", "Арбатская 7, Москва", 4.2, MetroStation(4, "Арбатская"), "1500", listOf("Wi-fi", "еда на вынос")),
            Restaurant(0, "Золотая бухта", "Украинская", "Арбатская 7, Москва", 4.2, MetroStation(1, "Юго-западная"), "1500", listOf("Wi-fi", "еда на вынос")),
            Restaurant(0, "Золотая бухта", "Украинская", "Арбатская 7, Москва", 4.2, MetroStation(4, "Арбатская"), "1500", listOf("Wi-fi", "еда на вынос"))
    )

    private var restaurantListAdapter: RestaurantListAdapter? = null

    override fun setAttr() = setLayout(R.layout.fragment_restaurant)

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_suggest)

        restaurantListAdapter = RestaurantListAdapter(restaurantsList)
        restaurantFragmentList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        restaurantFragmentList.adapter = restaurantListAdapter
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

}