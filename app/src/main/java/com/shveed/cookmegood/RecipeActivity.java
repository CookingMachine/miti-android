package com.shveed.cookmegood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shveed.cookmegood.fragments.IngredientFragment;
import com.shveed.cookmegood.fragments.KbjuFragment;
import com.shveed.cookmegood.fragments.RecipeFragment;
import com.shveed.wallpapperparser.R;

public class RecipeActivity extends AppCompatActivity {

    Button btnRecipe;
    Button btnIngred;
    Button btnKbju;

    TextView mainTitle;
    TextView lowerTitle;

    ImageView recipeImage;

    RecipeFragment recipeFragment;
    IngredientFragment ingredientFragment;
    KbjuFragment kbjuFragment;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String name = getIntent().getExtras().get("recipeName").toString();
        int image = Integer.valueOf(getIntent().getExtras().get("recipeImage").toString());

        ingredientFragment = new IngredientFragment();
        recipeFragment = new RecipeFragment();
        kbjuFragment = new KbjuFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameRecipe, recipeFragment);
        transaction.commit();

        mainTitle = (TextView) findViewById(R.id.recipesTextView);
        lowerTitle = (TextView) findViewById(R.id.textView);

        recipeImage = (ImageView) findViewById(R.id.imageView);

        btnRecipe = (Button) findViewById(R.id.recipeButton);
        btnIngred = (Button) findViewById(R.id.ingredButton);
        btnKbju = (Button) findViewById(R.id.kbjuButton);

        mainTitle.setText(name);
        lowerTitle.setText(name);

        recipeImage.setImageResource(image);

        View.OnClickListener recipeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecipe();
            }
        };
        View.OnClickListener ingredListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIngred(ingredientFragment);
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

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameRecipe, recipeFragment);
        fragmentTransaction.commit();
    }

    private void clickIngred(IngredientFragment ingredientFragment){
        btnRecipe.setBackgroundResource(R.drawable.rounded_corners_button);
        btnRecipe.setTextColor(getResources().getColor(R.color.objectsColor));

        btnIngred.setBackgroundResource(R.drawable.rounded_corners_button_inversed);
        btnIngred.setTextColor(getResources().getColor(R.color.backgroundColor));

        btnKbju.setBackgroundResource(R.drawable.rounded_corners_button);
        btnKbju.setTextColor(getResources().getColor(R.color.objectsColor));

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameRecipe, ingredientFragment);
        fragmentTransaction.commit();
    }

    private void clickKbju(){
        btnRecipe.setBackgroundResource(R.drawable.rounded_corners_button);
        btnRecipe.setTextColor(getResources().getColor(R.color.objectsColor));

        btnIngred.setBackgroundResource(R.drawable.rounded_corners_button);
        btnIngred.setTextColor(getResources().getColor(R.color.objectsColor));

        btnKbju.setBackgroundResource(R.drawable.rounded_corners_button_inversed);
        btnKbju.setTextColor(getResources().getColor(R.color.backgroundColor));

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameRecipe, kbjuFragment);
        fragmentTransaction.commit();
    }
}
