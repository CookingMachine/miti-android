package com.shveed.cookmegood.menu_fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.cookmegood.SuggestAdapter;
import com.shveed.cookmegood.activity.StartActivity;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;
import java.util.List;

public class SuggestFragment extends Fragment {

    private SuggestAdapter adapter;

    private EditText stepEditText;
    private EditText descriptionEditText;

    private Button clearButton;
    private Button addButton;
    private Button deleteStep;

    private RecyclerView recyclerView;

    public List<String> titles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_suggest, container, false);

        recyclerView = view.findViewById(R.id.stepRecycler);
        stepEditText = view.findViewById(R.id.stepEditName);
        descriptionEditText = view.findViewById(R.id.stepEditDesc);
        clearButton = view.findViewById(R.id.btnClearStep);
        addButton = view.findViewById(R.id.btnAddStep);

        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepEditText.setText("");
                descriptionEditText.setText("");
            }
        };

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        };

        View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        };

        clearButton.setOnClickListener(clearListener);
        addButton.setOnClickListener(addListener);

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
}
