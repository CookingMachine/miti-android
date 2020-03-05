package com.shveed.cookmegood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.shveed.cookmegood.R;

import java.util.ArrayList;

public class SuggestStepListAdapter extends BaseExpandableListAdapter {

    private Context context;

    private ArrayList<ArrayList<String>> listData;

    public SuggestStepListAdapter(Context context,
                                  ArrayList<ArrayList<String>> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getGroupCount() {
        return this.listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ArrayList<String> getGroup(int groupPosition) {
        return this.listData.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerText = (String) getGroup(groupPosition).get(0);
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_expandable_group, null);
        }

        TextView stepHeader = (TextView) convertView.findViewById(R.id.stepHeader);
        stepHeader.setText(headerText);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String stepTitleText = getChild(groupPosition, 1);
        String stepDescriptionText = getChild(groupPosition, 2);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_expandable_step, null);
        }

        TextView stepTitle = (TextView) convertView.findViewById(R.id.stepItemTitle);
        TextView stepDescription = (TextView) convertView.findViewById(R.id.stepItemDescription);
        stepTitle.setText(stepTitleText);
        stepDescription.setText(stepDescriptionText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
