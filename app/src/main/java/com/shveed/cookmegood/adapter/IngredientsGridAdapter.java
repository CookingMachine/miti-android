package com.shveed.cookmegood.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shveed.cookmegood.data.dto.Ingredient;
import com.shveed.cookmegood.R;

import java.util.HashMap;
import java.util.List;

public class IngredientsGridAdapter extends RecyclerView.Adapter<IngredientsGridAdapter.ViewHolder> {

    private List<Ingredient> ingredients;

    private HashMap<Ingredient, Integer> buyMap = new HashMap<>();

    private LayoutInflater mInflater;
    private IngredientsGridAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public IngredientsGridAdapter(Context context, List<Ingredient> ingredients) {
        this.mInflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
    }
    // inflates the row layout from xml when needed
    @Override
    public IngredientsGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsGridAdapter.ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(IngredientsGridAdapter.ViewHolder holder, int position) {
        String name = ingredients.get(position).getName();
        String amount = ingredients.get(position).getAmount();
        holder.nameView.setText(name);
        holder.amountView.setText(amount);
    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;
        TextView amountView;
        Button addToBuyList;

        ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.ingredText);
            amountView = itemView.findViewById(R.id.ingredAmount);
            addToBuyList = itemView.findViewById(R.id.addIngredientButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                if(view.getId() == addToBuyList.getId()){
                    Ingredient currIngredient = getItem(getAdapterPosition());
                    if(buyMap.containsKey(currIngredient)){
                        Integer value = buyMap.get(currIngredient);
                        buyMap.remove(currIngredient);
                        buyMap.put(currIngredient, value + 1);
                    }
                    else{
                        buyMap.put(currIngredient, 1);
                    }
                }
                else {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            }
        }
    }
    // convenience method for getting data at click position
    Ingredient getItem(int id) {
        return ingredients.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(IngredientsGridAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public HashMap<Ingredient, Integer> getBuyMap() {
        return buyMap;
    }

    public void setBuyMap(HashMap<Ingredient, Integer> buyMap) {
        this.buyMap = buyMap;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
