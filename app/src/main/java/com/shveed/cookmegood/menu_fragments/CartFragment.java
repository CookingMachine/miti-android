package com.shveed.cookmegood.menu_fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.CartRecipeAdapter;
import com.shveed.cookmegood.entity.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    List<Recipe> recipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_cart, container, false);
        setRecipeData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cartRecycler);
        CartRecipeAdapter adapter = new CartRecipeAdapter(getContext(), recipes);
        recyclerView.setAdapter(adapter);
        return view;
    }


    private void setRecipeData(){
        recipes.add(new Recipe("Борщ", "Украина", R.drawable.pic1));
        recipes.add(new Recipe("Пицца", "Италия", R.drawable.pic1));
        recipes.add(new Recipe("Плов", "Узбекистан", R.drawable.pic1));
        recipes.add(new Recipe("Лаваш", "Армения", R.drawable.pic1));
    }
}
