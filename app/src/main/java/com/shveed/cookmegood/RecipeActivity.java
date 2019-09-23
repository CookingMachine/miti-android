package com.shveed.cookmegood;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private String localLink = "https://kedem.ru";
    private ArrayList<String> recipeNames = new ArrayList<>();
    private ArrayList<String> recipeImages = new ArrayList<>();
    public ArrayList<RecipeItem> recipeItems;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    public class ParseRecipe extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... arg){
            try{
                Document page = Jsoup.connect(localLink).get();
                Elements recipesOnCurrTheme = page.select(
                        ".w-clearfix w-inline-block pgrblock");
                for(Element elem: recipesOnCurrTheme){
                    recipeNames.add(elem.select("a")
                            .attr("alt"));
                    recipeImages.add(elem.select("a")
                            .attr("src"));
                }
            }
            catch(IOException ioe){}
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.resultText);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        localLink += getIntent().getStringExtra("url");

        ParseRecipe parser = new ParseRecipe();
        parser.execute();



        setInitialData();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recipesList);
        DataAdapter adapter = new DataAdapter(this, recipeItems);
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData(){
        for(int i = 0; i < recipeNames.size(); i++){
            recipeItems.add(
                    new RecipeItem(getIntent().getStringExtra("theme"),
                    recipeNames.get(i),
                    recipeImages.get(i)));
        }
    }

}
