package com.shveed.cookmegood;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import java.util.List;

public class RecipesGridAdapter extends RecyclerView.Adapter<RecipesGridAdapter.ViewHolder> {

    private List<String> mData;

    private String amount = "101 позиция";

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecipesGridAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position);
        holder.nameView.setText(name);
        holder.amountView.setText(amount);

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onUpdateList(List<String> newList){
        this.mData = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;
        TextView amountView;

        ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.recipeName);
            amountView = itemView.findViewById(R.id.recipeAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
