package com.shveed.cookmegood;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder>{

    private List<String> steps;

    private LayoutInflater mInflater;
    private SuggestAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public SuggestAdapter(Context context, List<String> steps) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @Override
    public SuggestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_suggest_step, parent, false);
        return new SuggestAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SuggestAdapter.ViewHolder holder, int position) {
        String title = steps.get(position);
        holder.stepTitle.setText(title);
        holder.stepNum.setText(String.valueOf(position + 1));
    }
    @Override
    public int getItemCount() {
        return steps.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepTitle;
        TextView stepNum;

        ViewHolder(View itemView) {
            super(itemView);
            stepTitle = itemView.findViewById(R.id.stepTitle);
            stepNum = itemView.findViewById(R.id.stepNum);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return steps.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(SuggestAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

