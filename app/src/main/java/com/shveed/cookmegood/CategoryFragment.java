package com.shveed.cookmegood;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shveed.cookmegood.activity.RecipeActivity;
import com.shveed.cookmegood.activity.SearchActivity;
import com.shveed.cookmegood.entity.Recipe;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.cookmegood.menu_fragments.MainFragment;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private List<Recipe> recipes = new ArrayList<>();

    Button backButton;
    Button searchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_category, container, false);
        setRecipeData();

        backButton = (Button) view.findViewById(R.id.backButton);
        searchButton = (Button) view.findViewById(R.id.searchButton);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recipeRecycler);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment mainFragment = new MainFragment();
                FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                fc.replaceFragment(mainFragment);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

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
    public void goToast(String output){
        Toast errorToast = Toast.makeText(getActivity(),
                output, Toast.LENGTH_SHORT);
        errorToast.show();
    }
}
