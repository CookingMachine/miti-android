package com.shveed.cookmegood.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shveed.cookmegood.CategoryFragment;
import com.shveed.cookmegood.RecipesGridAdapter;
import com.shveed.cookmegood.data.NetworkService;
import com.shveed.cookmegood.data.RuntimeStorage;
import com.shveed.cookmegood.entity.Category;
import com.shveed.cookmegood.interfaces.FragmentChangeListener;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements RecipesGridAdapter.ItemClickListener {

    private RecipesGridAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<String> data = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_main, container, false);

        progressBar = view.findViewById(R.id.mainFragmentProgressBar);

        data = RuntimeStorage.newInstance().categories;

        recyclerView = view.findViewById(R.id.mainFragmentRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecipesGridAdapter(getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        try {
            getCategoriesFromServer();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return view;

    }
    @Override
    public void onItemClick(View view, int position) {
        Fragment category = new CategoryFragment();
        FragmentChangeListener fc = (FragmentChangeListener)getActivity();
        fc.replaceFragment(category);
    }

    private void getCategoriesFromServer(){
        NetworkService.getInstance()
                .getCategoryApi()
                .getAllCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                        showList();

                        for(Category category: response.body()){
                            RuntimeStorage.newInstance().categories.add(category.getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                        showList();

                        Toast.makeText(getContext(), "Нет связи с сервером", Toast.LENGTH_SHORT).show();

                        RuntimeStorage.newInstance().categories =
                                Arrays.asList("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки");

                        adapter.onUpdateList(RuntimeStorage.newInstance().categories);
                    }
                });
    }

    private void showList(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
