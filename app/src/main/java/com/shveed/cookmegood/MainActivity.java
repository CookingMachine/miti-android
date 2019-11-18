package com.shveed.cookmegood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesGridAdapter.ItemClickListener{
    private TextView mTextMessage;
    private Spinner themeSpinner;
    private Button goToRecipe;

    private RecipesGridAdapter adapter;

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

        BottomNavigationView navView = findViewById(R.id.nav_view_main);

        List<String> data = Arrays.asList("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки");
        RecyclerView recyclerView = findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecipesGridAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        User user = (User)getIntent().getSerializableExtra("userObject");
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(intent);
    }
}
