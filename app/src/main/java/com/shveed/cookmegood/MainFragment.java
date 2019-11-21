package com.shveed.cookmegood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.wallpapperparser.R;

import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment implements RecipesGridAdapter.ItemClickListener{

    private RecipesGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_main, container, false);
        List<String> data = Arrays.asList("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки");
        RecyclerView recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecipesGridAdapter(getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        startActivity(intent);
    }
}
