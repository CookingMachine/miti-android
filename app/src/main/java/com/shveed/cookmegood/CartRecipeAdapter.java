package com.shveed.cookmegood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

import java.util.List;

class CartRecipeAdapter extends RecyclerView.Adapter<CartRecipeAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Recipe> recipes;

    CartRecipeAdapter(Context context, List<Recipe> recipes) {
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public CartRecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_recycler_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartRecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.imageView.setImageResource(R.drawable.pic1);
        holder.nameView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.cartRecipeImage);
            nameView = (TextView) view.findViewById(R.id.cartRecipeName);
        }
    }
}
