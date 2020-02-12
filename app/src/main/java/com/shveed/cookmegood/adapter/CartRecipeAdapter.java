package com.shveed.cookmegood.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shveed.cookmegood.entity.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.List;

public class CartRecipeAdapter extends RecyclerView.Adapter<CartRecipeAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Recipe> recipes;

    public CartRecipeAdapter(Context context, List<Recipe> recipes) {
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public CartRecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart_recipe, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.cartRecipeImage);
            nameView = (TextView) view.findViewById(R.id.cartRecipeName);
        }
    }
}
