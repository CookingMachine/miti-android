package com.shveed.cookmegood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.shveed.wallpapperparser.R;

public class RecipeActivity extends AppCompatActivity {

    Button btnRecipe;
    Button btnIngred;
    Button btnKbju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        GridView gridView = (GridView) findViewById(R.id.gridIngred);
        gridView.setAdapter(new IngredientAdapter(this));




        btnRecipe = (Button) findViewById(R.id.recipeButton);
        btnIngred = (Button) findViewById(R.id.ingredButton);
        btnKbju = (Button) findViewById(R.id.kbjuButton);
        View.OnClickListener recipeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecipe();
            }
        };
        View.OnClickListener ingredListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIngred();
            }
        };
        View.OnClickListener kbjuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickKbju();
            }
        };
        btnRecipe.setOnClickListener(recipeListener);
        btnIngred.setOnClickListener(ingredListener);
        btnKbju.setOnClickListener(kbjuListener);
    }

    private void clickRecipe(){
        btnRecipe.setBackgroundResource(R.drawable.rounded_corners_button_inversed);
        btnRecipe.setTextColor(getResources().getColor(R.color.backgroundColor));

        btnIngred.setBackgroundResource(R.drawable.rounded_corners_button);
        btnIngred.setTextColor(getResources().getColor(R.color.objectsColor));

        btnKbju.setBackgroundResource(R.drawable.rounded_corners_button);
        btnKbju.setTextColor(getResources().getColor(R.color.objectsColor));
    }

    private void clickIngred(){
        btnRecipe.setBackgroundResource(R.drawable.rounded_corners_button);
        btnRecipe.setTextColor(getResources().getColor(R.color.objectsColor));

        btnIngred.setBackgroundResource(R.drawable.rounded_corners_button_inversed);
        btnIngred.setTextColor(getResources().getColor(R.color.backgroundColor));

        btnKbju.setBackgroundResource(R.drawable.rounded_corners_button);
        btnKbju.setTextColor(getResources().getColor(R.color.objectsColor));
    }

    private void clickKbju(){
        btnRecipe.setBackgroundResource(R.drawable.rounded_corners_button);
        btnRecipe.setTextColor(getResources().getColor(R.color.objectsColor));

        btnIngred.setBackgroundResource(R.drawable.rounded_corners_button);
        btnIngred.setTextColor(getResources().getColor(R.color.objectsColor));

        btnKbju.setBackgroundResource(R.drawable.rounded_corners_button_inversed);
        btnKbju.setTextColor(getResources().getColor(R.color.backgroundColor));
    }


}
