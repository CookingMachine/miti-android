package com.shveed.cookmegood;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shveed.cookmegood.activity.RecipeActivity;
import com.shveed.cookmegood.activity.SearchActivity;
import com.shveed.cookmegood.entity.Recipe;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.cookmegood.fragment_activity.MainFragment;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryFragment extends Fragment {

    private List<Recipe> recipes = new ArrayList<>();

    @BindView(R.id.backButton) Button backButton;
    @BindView(R.id.searchButton) Button searchButton;
    @BindView(R.id.recipeRecycler) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_category, container, false);
        ButterKnife.bind(this, view);

        setRecipeData();

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

    @OnClick(R.id.backButton)
    public void backButtonListener(Button button){
        Fragment mainFragment = new MainFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(mainFragment);
    }

    @OnClick(R.id.searchButton)
    public void searchButtonListener(Button button){
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }
}
