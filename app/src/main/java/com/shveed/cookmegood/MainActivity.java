package com.shveed.cookmegood;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private Spinner themeSpinner;
    private final String LINK1 = "https://www.bankreceptov.ru/";
    private HashMap<String, String> themesURLs = new HashMap<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.resultText);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        themeSpinner = (Spinner) findViewById(R.id.themespinner);

        ParsePageThemes parsePage = new ParsePageThemes();
        parsePage.execute();
        String[] parsedThemes = null;
        try {
            parsedThemes = parsePage.get().split(",");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_dropdown_item, parsedThemes);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        themeSpinner.setAdapter(adapterSpinner);
    }
    /*
    * Кнопка "показать". Выбирает текущую тему из Spinner, перекидывает
    * пользователя на соответствующую Activity и передаёт туда ссылку на
    * тему и название темы. Ссылки хранятся в HashMap после парса тем.
    * Найти каждую ссылку можно по ключу (теме).
    * */
    public void onButtonClick(View v){
        String currentTheme = "";
        if(themeSpinner.getSelectedItem() != ""){
            currentTheme = themeSpinner.getSelectedItem().toString();
            Intent toRecipe = new Intent(MainActivity.this, RecipeActivity.class);
            toRecipe.putExtra("url", themesURLs.get(currentTheme));
            toRecipe.putExtra("theme", currentTheme);
            startActivity(toRecipe);
        }
        else{
            AlertDialog.Builder chooseThemeDialog = new AlertDialog.Builder(MainActivity.this);
            chooseThemeDialog.setTitle("Что ищем-то?")
                    .setMessage("Вы забыли выбрать тему!")
                    .setCancelable(false)
                    .setNegativeButton("Уже выбираю...",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = chooseThemeDialog.create();
            alert.show();
        }
    }

    /*
    *  Парсим страницу с меню для сбора тем
    * */

    public class ParsePageThemes extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg) {
            String pageLine = "";
            try {
                Document page = Jsoup.connect(LINK1).get();
                Elements themeElements = page.select(".mdv").select("a");
                String pageRefs = "";
                Element currElem = themeElements.first();
                for (int i = 0; i < 29; i++) {
                    //Log.d("CHECKPOINT", currElem.text());
                    if(currElem.text() != "") {
                        pageLine += currElem.text() + ",";
                        pageRefs += currElem.attr("href") + ",";
                        themesURLs.put(currElem.text(), currElem.attr("href"));
                    }
                    currElem = currElem.nextElementSibling();
                }
            }
            catch(IOException ioe){}
            return pageLine;
        }
    }
}
