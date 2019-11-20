package com.shveed.cookmegood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.shveed.cookmegood.entity.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setRecipeData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cartRecycler);
        CartRecipeAdapter adapter = new CartRecipeAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
    }


    private void setRecipeData(){
        recipes.add(new Recipe("Борщ", "Украина", R.drawable.pic1));
        recipes.add(new Recipe("Пицца", "Италия", R.drawable.pic1));
        recipes.add(new Recipe("Плов", "Узбекистан", R.drawable.pic1));
        recipes.add(new Recipe("Лаваш", "Армения", R.drawable.pic1));
    }
}
