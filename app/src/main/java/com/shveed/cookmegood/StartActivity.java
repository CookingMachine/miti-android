package com.shveed.cookmegood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shveed.cookmegood.entity.User;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.wallpapperparser.R;

public class StartActivity extends FragmentActivity implements FragmentChangeListener {

    private User currentUser;

    private Fragment selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = new MainFragment();
            switch (item.getItemId()) {
                case R.id.navigation_suggest:
                    selectedFragment = new SuggestFragment();
                    break;
                case R.id.navigation_favourites:
                    selectedFragment = new FavouritesFragment();
                    break;
                case R.id.navigation_recipe:
                    selectedFragment = new MainFragment();
                    break;
                case R.id.navigation_cart:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new CabinetFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.f_start, selectedFragment)
            .commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportFragmentManager().beginTransaction().replace(R.id.f_start, new MainFragment())
                .commit();

        BottomNavigationView navView = findViewById(R.id.nav_view_start);
        navView.setOnNavigationItemSelectedListener(navListener);

        currentUser = (User)getIntent().getSerializableExtra("userObject");
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.f_start, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void toSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void toCategories(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.f_start, new MainFragment())
                .commit();
    }
}
