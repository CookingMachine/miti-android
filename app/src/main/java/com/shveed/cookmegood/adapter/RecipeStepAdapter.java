package com.shveed.cookmegood.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shveed.cookmegood.data.dto.Step;
import com.shveed.cookmegood.R;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder>{

    private List<Step> steps;

    private LayoutInflater mInflater;
    private RecipeStepAdapter.ItemClickListener mClickListener;

    public RecipeStepAdapter(Context context, List<Step> steps) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @Override
    public RecipeStepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeStepAdapter.ViewHolder(view);
    }

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

    Step getItem(int id) {
        return steps.get(id);
    }

    void setClickListener(RecipeStepAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
