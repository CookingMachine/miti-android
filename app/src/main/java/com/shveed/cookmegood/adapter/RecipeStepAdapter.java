package com.shveed.cookmegood.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shveed.cookmegood.entity.Step;
import com.shveed.wallpapperparser.R;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder>{

    private List<Step> steps;

    private LayoutInflater mInflater;
    private RecipeStepAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecipeStepAdapter(Context context, List<Step> steps) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @Override
    public RecipeStepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.f_recipe_element, parent, false);
        return new RecipeStepAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecipeStepAdapter.ViewHolder holder, int position) {
        String number = String.valueOf(steps.get(position).getStepNumber());
        String title = steps.get(position).getTitle();
        String description = steps.get(position).getDescription();
        holder.numberView.setText(number);
        holder.titleView.setText(title);
        holder.descriptionView.setText(description);
    }
    @Override
    public int getItemCount() {
        return steps.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView numberView;
        TextView titleView;
        TextView descriptionView;

        ViewHolder(View itemView) {
            super(itemView);
            numberView = itemView.findViewById(R.id.recipeStepNumber);
            titleView = itemView.findViewById(R.id.recipeStepTitle);
            descriptionView = itemView.findViewById(R.id.recipeStepDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    Step getItem(int id) {
        return steps.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecipeStepAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
