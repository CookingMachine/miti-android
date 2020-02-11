package com.shveed.cookmegood.fragment_activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shveed.cookmegood.CategoryFragment;
import com.shveed.cookmegood.RecipesGridAdapter;
import com.shveed.cookmegood.data.RuntimeStorage;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements RecipesGridAdapter.ItemClickListener {

    private RecipesGridAdapter adapter;
    private List<String> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_main, container, false);

        data = RuntimeStorage.newInstance().categories;

        RecyclerView recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecipesGridAdapter(getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;

    }
    @Override
    public void onItemClick(View view, int position) {
        Fragment category = new CategoryFragment();
        FragmentChangeListener fc = (FragmentChangeListener)getActivity();
        fc.replaceFragment(category);
    }


}
