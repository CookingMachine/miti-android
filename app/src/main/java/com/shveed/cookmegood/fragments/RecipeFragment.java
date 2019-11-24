package com.shveed.cookmegood.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.RecipeStepAdapter;
import com.shveed.cookmegood.entity.Step;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    List<Step> steps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_recipe, container, false);
        setData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recipeStepRecycler);
        RecipeStepAdapter adapter = new RecipeStepAdapter(getActivity(), steps);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setData(){
        for(int i = 1; i < 5; i++) {
            this.steps.add(new Step(i,
                    "неторопясь нарезаем вкусненькую отваренную курочку",
                    "нарезаем курицу"));
        }
    }
}
