package com.shveed.cookmegood;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.shveed.wallpapperparser.R;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_object);
        Log.d("CHECKPOINT", "ACTIVITY LOADED");

        GridView gridView = (GridView) findViewById(R.id.gridIngred);
        gridView.setAdapter(new IngredientAdapter(this));
    }
}
