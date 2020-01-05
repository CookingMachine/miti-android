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

import com.shveed.cookmegood.data.NetworkService;
import com.shveed.cookmegood.data.RuntimeStorage;
import com.shveed.cookmegood.entity.Category;
import com.shveed.cookmegood.entity.User;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.cookmegood.menu_fragments.CabinetFragment;
import com.shveed.cookmegood.menu_fragments.CartFragment;
import com.shveed.cookmegood.menu_fragments.FavouritesFragment;
import com.shveed.cookmegood.menu_fragments.MainFragment;
import com.shveed.cookmegood.menu_fragments.SuggestFragment;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.f_start, selectedFragment)
            .commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            getCategories();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fragments.add(new SuggestFragment());
        fragments.add(new FavouritesFragment());
        fragments.add(new MainFragment());
        fragments.add(new CartFragment());
        fragments.add(new CabinetFragment());

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

    public void getCategories(){
        NetworkService.getInstance()
                .getCategoryApi()
                .getAllCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        for(Category category: response.body()){
                            RuntimeStorage.newInstance().categories.add(category.getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        RuntimeStorage.newInstance().categories =
                                Arrays.asList("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки");
                    }
                });
    }
}
