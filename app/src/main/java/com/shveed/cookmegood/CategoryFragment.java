package com.shveed.cookmegood;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.entity.Recipe;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private List<Recipe> recipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_category, container, false);

        setRecipeData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recipeRecycler);
        recyclerView.addOnItemTouchListener(
                new RecipeItemClickListener(getContext(), recyclerView,
                        new RecipeItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String name = recipes.get(position).getName();
                        int image = recipes.get(position).getImage();
                        toRecipe(name, image);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        String name = recipes.get(position).getName();
                        int image = recipes.get(position).getImage();
                        toRecipe(name, image);
                    }
                })
        );

        CategoryRecipeAdapter adapter = new CategoryRecipeAdapter(getContext(), recipes);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setRecipeData(){
        recipes.add(new Recipe("Борщ", "Украина", R.drawable.pic2));
        recipes.add(new Recipe("Пицца", "Италия", R.drawable.pic2));
        recipes.add(new Recipe("Плов", "Узбекистан", R.drawable.pic2));
        recipes.add(new Recipe("Лаваш", "Армения", R.drawable.pic2));
    }
    public void toRecipe(String name, int image){
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        intent.putExtra("recipeName", name);
        intent.putExtra("recipeImage", image);
        startActivity(intent);
    }
}
