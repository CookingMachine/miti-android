package com.shveed.cookmegood.menu_fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shveed.cookmegood.CategoryFragment;
import com.shveed.cookmegood.RecipesGridAdapter;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.wallpapperparser.R;

import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment implements RecipesGridAdapter.ItemClickListener {

    private RecipesGridAdapter adapter;
    private TextView nameView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.roboto_thin);
//        nameView.findViewById(R.id.recipeName);
//        nameView.setTypeface(typeface);
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
        String recipe = adapter.getItem(position);
        Fragment category = new CategoryFragment();
        FragmentChangeListener fc = (FragmentChangeListener)getActivity();
        fc.replaceFragment(category);
    }
}
