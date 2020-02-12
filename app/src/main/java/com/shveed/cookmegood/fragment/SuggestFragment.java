package com.shveed.cookmegood.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.shveed.cookmegood.adapter.SuggestStepListAdapter;
import com.shveed.wallpapperparser.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestFragment extends Fragment {

    @BindView(R.id.stepEditName) EditText stepEditText;
    @BindView(R.id.stepEditDesc) EditText descriptionEditText;

    @BindView(R.id.btnClearStep) Button clearButton;
    @BindView(R.id.btnAddStep) Button addButton;

    @BindView(R.id.stepExpandable) ExpandableListView stepListView;
    Button deleteStep;

    public ArrayList<ArrayList<String>> stepHeadersList = new ArrayList<>();

    SuggestStepListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_suggest, container, false);

        ButterKnife.bind(this, view);

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
    public void addStep(){
        if(!stepEditText.getText().toString().equals("") &&
                !descriptionEditText.getText().toString().equals("")) {
            ArrayList<String> step = new ArrayList<>();
            String stepName = stepEditText.getText().toString();
            String stepDescription = descriptionEditText.getText().toString();
            step.add("Шаг " + (stepHeadersList.size() + 1));
            step.add(stepName);
            step.add(stepDescription);

            stepHeadersList.add(step);

            stepListView.setAdapter(new SuggestStepListAdapter(getContext(), stepHeadersList));

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
