package com.shveed.cookmegood.recipe_page_fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.IngredientsGridAdapter;
import com.shveed.cookmegood.entity.Ingredient;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientFragment extends Fragment {

    List<Ingredient> ingredients;

    private String[] data = {"Помидоры", "Салат", "Хлеб", "Майонез", "Чеснок", "Сыр", "Укроп", "Лук"};

    private String[] amount = {"400г", "200г", "1 буханка", "200г", "2 головки", "300г", "50г", "50г"};

    private HashMap<Ingredient, Integer> buyMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ingredients = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            Ingredient ingredient = new Ingredient(data[i], amount[i]);
            this.ingredients.add(ingredient);
        }
        View view = inflater.inflate(R.layout.f_ingredient, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gridIngred);
        IngredientsGridAdapter adapter = new IngredientsGridAdapter(getActivity(), ingredients);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public HashMap<Ingredient, Integer> getBuyMap() {
        return buyMap;
    }
}