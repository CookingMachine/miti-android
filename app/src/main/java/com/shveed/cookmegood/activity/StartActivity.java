package com.shveed.cookmegood.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shveed.cookmegood.entity.User;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.cookmegood.fragment.CabinetFragment;
import com.shveed.cookmegood.fragment.CartFragment;
import com.shveed.cookmegood.fragment.FavouritesFragment;
import com.shveed.cookmegood.fragment.MainFragment;
import com.shveed.cookmegood.fragment.SuggestFragment;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends FragmentActivity implements FragmentChangeListener {

    private User currentUser;

    private Fragment selectedFragment;

    private List<Fragment> fragments = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = new MainFragment();
            switch (item.getItemId()) {
                case R.id.navigation_suggest:
                    selectedFragment = fragments.get(0);
                    break;
                case R.id.navigation_favourites:
                    selectedFragment = fragments.get(1);
                    break;
                case R.id.navigation_recipe:
                    selectedFragment = fragments.get(2);
                    break;
                case R.id.navigation_cart:
                    selectedFragment = fragments.get(3);
                    break;
                case R.id.navigation_profile:
                    selectedFragment = fragments.get(4);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentStartFrameLayout, selectedFragment)
            .commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fragments.add(new SuggestFragment());
        fragments.add(new FavouritesFragment());
        fragments.add(new MainFragment());
        fragments.add(new CartFragment());
        fragments.add(new CabinetFragment());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentStartFrameLayout, new MainFragment())
                .commit();

        BottomNavigationView navView = findViewById(R.id.nav_view_start);
        navView.setOnNavigationItemSelectedListener(navListener);

        currentUser = (User)getIntent().getSerializableExtra("userObject");
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentStartFrameLayout, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void toSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void toCategories(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentStartFrameLayout, new MainFragment())
                .commit();
    }
}
