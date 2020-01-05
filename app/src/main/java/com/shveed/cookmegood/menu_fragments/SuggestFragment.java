package com.shveed.cookmegood.menu_fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.cookmegood.SuggestAdapter;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestFragment extends Fragment {

    private SuggestAdapter adapter;

    @BindView(R.id.stepEditName) EditText stepEditText;
    @BindView(R.id.stepEditDesc) EditText descriptionEditText;

    @BindView(R.id.btnClearStep) Button clearButton;
    @BindView(R.id.btnAddStep) Button addButton;
    Button deleteStep;

    @BindView(R.id.stepRecycler) RecyclerView recyclerView;

    public List<String> titles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_suggest, container, false);

        ButterKnife.bind(this, view);

        adapter = new SuggestAdapter(getContext(), titles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void goToast(String output){
        Toast errorToast = Toast.makeText(getContext(),
                output, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    @OnClick(R.id.btnClearStep)
    public void clearFields(Button button){
        stepEditText.setText("");
        descriptionEditText.setText("");
    }

    @OnClick(R.id.btnAddStep)
    public void addStep(Button button){
        if(!stepEditText.getText().toString().equals("") &&
                !descriptionEditText.getText().toString().equals("")) {
            String stepName = stepEditText.getText().toString();
            String stepDescription = descriptionEditText.getText().toString();

            titles.add(stepName);
            recyclerView.setAdapter(new SuggestAdapter(getContext(), titles));

            stepEditText.setText("");
            descriptionEditText.setText("");
        }
        else{
            goToast("Заполните оба поля");
        }
    }

    public void deleteStep(Button button){

    }
}
