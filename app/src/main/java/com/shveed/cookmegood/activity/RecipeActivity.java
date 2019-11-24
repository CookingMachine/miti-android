package com.shveed.cookmegood.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shveed.cookmegood.entity.Ingredient;
import com.shveed.cookmegood.recipe_page_fragments.IngredientFragment;
import com.shveed.cookmegood.recipe_page_fragments.KbjuFragment;
import com.shveed.cookmegood.recipe_page_fragments.RecipeFragment;
import com.shveed.wallpapperparser.R;

import java.util.HashMap;

public class RecipeActivity extends AppCompatActivity {

    private int portions = 1;

    Button btnRecipe;
    Button btnIngred;
    Button btnKbju;

    TextView lowerTitle;
    TextView portionText;

    ImageView recipeImage;

    RecipeFragment recipeFragment;
    IngredientFragment ingredientFragment;
    KbjuFragment kbjuFragment;

    FragmentTransaction fragmentTransaction;

    private HashMap<Ingredient, Integer> buyMap = new HashMap<>();

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

        lowerTitle = (TextView) findViewById(R.id.textView);
        portionText = (TextView) findViewById(R.id.countPor);

        recipeImage = (ImageView) findViewById(R.id.imageView);

        btnRecipe = (Button) findViewById(R.id.recipeButton);
        btnIngred = (Button) findViewById(R.id.ingredButton);
        btnKbju = (Button) findViewById(R.id.kbjuButton);

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

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameRecipe, recipeFragment);
        fragmentTransaction.commit();
    }

    private void clickIngred(){
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

    public void countPortion(View view){
        String line;
        if(view.getId() == R.id.minusButton){
            if(portions > 1){
                portions--;
                line = "Порции: " + portions;
                portionText.setText(line);
            }
        }
        else if(view.getId() == R.id.plusButton){
            portions++;
            line = "Порции: " + portions;
            portionText.setText(line);
        }
    }
}
