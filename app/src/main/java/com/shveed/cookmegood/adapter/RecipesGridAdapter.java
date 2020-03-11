package com.shveed.cookmegood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shveed.wallpapperparser.R;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position);
        holder.nameView.setText(name);
        holder.amountView.setText(amount);

        holder.layout.setLayoutParams(setMargins(holder, position % 2 == 0));

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
        RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.recipeName);
            amountView = itemView.findViewById(R.id.recipeAmount);
            layout = itemView.findViewById(R.id.gridRecipeLayout);
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

    private ViewGroup.LayoutParams setMargins(ViewHolder holder, boolean isEven){
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.layout.getLayoutParams();
        if(isEven){
            p.setMargins(15, 20, 15, 0);
        }
        else{
            p.setMargins(15, 20, 15, 0);
        }
        return p;
    }
}
