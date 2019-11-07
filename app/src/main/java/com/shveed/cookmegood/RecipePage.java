package com.shveed.cookmegood;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.shveed.wallpapperparser.R;

public class RecipePage extends AppCompatActivity {
    private GridView gridView;
    String[] data = {"Помидорчик", "Салатик", "Хлеб", "Мазик", "Чесночёк", "Сырок"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_object);
        Log.d("CHECKPOINT", "ACTIVITY LOADED");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grid_element, R.id.ingredText, data); //error here
//        gridView.setAdapter(adapter);
//        initGridView();
    }

    private void initGridView(){

    }
}
