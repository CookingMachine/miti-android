package com.shveed.cookmegood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shveed.cookmegood.entity.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setRecipeData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipeRecycler);
        recyclerView.addOnItemTouchListener(
                new RecipeItemClickListener(this, recyclerView,
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

        CategoryRecipeAdapter adapter = new CategoryRecipeAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
    }

    private void setRecipeData(){
        recipes.add(new Recipe("Борщ", "Украина", R.drawable.pic1));
        recipes.add(new Recipe("Пицца", "Италия", R.drawable.pic1));
        recipes.add(new Recipe("Плов", "Узбекистан", R.drawable.pic1));
        recipes.add(new Recipe("Лаваш", "Армения", R.drawable.pic1));
    }
    public void toRecipe(String name, int image){
        Intent intent = new Intent(CategoryActivity.this, RecipeActivity.class);
        intent.putExtra("recipeName", name);
        intent.putExtra("recipeImage", image);
        startActivity(intent);
    }
    public void toCart(View view){
        Intent intent = new Intent(CategoryActivity.this, CartActivity.class);
        startActivity(intent);
    }
}
