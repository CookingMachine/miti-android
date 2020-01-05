package com.shveed.cookmegood.recipe_page_fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.RecipeStepAdapter;
import com.shveed.cookmegood.entity.Step;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    List<Step> steps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        steps  = new ArrayList<>();
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
                    "Неторопясь нарезаем вкусненькую отваренную курочку",
                    "Нарезаем курицу"));
        }
    }
}
