package com.shveed.cookmegood;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecipeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private String localLink = "https://www.bankreceptov.ru";
    private String localTheme = "";
    private ArrayList<String> recipeNames = new ArrayList<>();
    private ArrayList<String> recipeHrefs = new ArrayList<>();
    public ArrayList<RecipeItem> recipeItems = new ArrayList<>();

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
            String pagePairs = "";
            try{
                Document page = Jsoup.connect(localLink).get();
                Elements recipesOnCurrTheme = page.select(
                        ".t11").select("ul").select("li").select("a");
                for(Element elem: recipesOnCurrTheme){
                    pagePairs += elem.text() + '|' + (localLink + elem.attr("href")) + ';';
                }

            }
            catch(IOException ioe){}
            return pagePairs;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        localTheme = getIntent().getExtras().getString("theme");
        localLink += getIntent().getExtras().getString("url");
        mTextMessage = findViewById(R.id.resultText);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ParseRecipe parser = new ParseRecipe();
        parser.execute();

        String recipeData[] = null;

        try {
            recipeData = parser.get().split(";");
            for(int i = 0; i < recipeData.length; i++){
                recipeNames.add(recipeData[i].substring(0, recipeData[i].indexOf('|')));
                recipeHrefs.add(recipeData[i].substring(recipeData[i].indexOf('|') + 1));
            }
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setInitialData();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recipesList);
        DataAdapter adapter = new DataAdapter(this, recipeItems);
        recyclerView.setAdapter(adapter);

    }

    private void setInitialData(){
        for(int i = 0; i < recipeNames.size(); i++){
            recipeItems.add(
                    new RecipeItem(localTheme,
                    recipeNames.get(i),
                    recipeHrefs.get(i)));
        }
    }

}
