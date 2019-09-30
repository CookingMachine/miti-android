package com.shveed.cookmegood;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.shveed.wallpapperparser.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipePage extends AppCompatActivity {
    ExpandableListView expandableListView;
    MainAdapter adapter;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    ArrayList<String> recipeNames = new ArrayList<>();
    ArrayList<String> recipeHrefs = new ArrayList<>();
    String localTheme;
    String localLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);
        expandableListView = findViewById(R.id.expandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();

        adapter = new MainAdapter(this, listGroup,listItem);
        expandableListView.setAdapter(adapter);

        localTheme = getIntent().getExtras().getString("theme");
        localLink += getIntent().getExtras().getString("url");

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

        initListData();
    }

    public class ParseRecipe extends AsyncTask<String, Void, String> {
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

    private void initListData() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Cheeeeese");
        for(int i = 0; i < recipeNames.size(); i++){
            listItem.put(recipeNames.get(i), ingredients);
        }
        adapter.notifyDataSetChanged();
    }

}
