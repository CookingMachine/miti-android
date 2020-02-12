package com.shveed.cookmegood.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shveed.cookmegood.data.dto.Recipe;
import com.shveed.wallpapperparser.R;

import java.util.List;

public class CategoryRecipeAdapter extends RecyclerView.Adapter<CategoryRecipeAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Recipe> recipes;

    public CategoryRecipeAdapter(Context context, List<Recipe> recipes) {
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public CategoryRecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryRecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.imageView.setImageResource(recipe.getImage());
        holder.nameView.setText(recipe.getName());
        holder.kitchenView.setText(recipe.getKitchen());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, kitchenView;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.recipeImage);
            nameView = (TextView) view.findViewById(R.id.recipeTitle);
            kitchenView = (TextView) view.findViewById(R.id.recipeNationality);
        }
    }
}
