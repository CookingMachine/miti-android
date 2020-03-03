package com.shveed.cookmegood.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.adapter.CartRecipeAdapter;
import com.shveed.cookmegood.data.dto.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private List<Recipe> recipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_cart, container, false);
        setRecipeData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cartRecycler);
        CartRecipeAdapter adapter = new CartRecipeAdapter(recipes, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }


    private void setRecipeData(){
    }
}
