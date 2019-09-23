package com.shveed.cookmegood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

import com.shveed.wallpapperparser.R;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<RecipeItem> Recipes;

    DataAdapter(Context context, List<RecipeItem> recipes) {
        this.Recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        RecipeItem currRec = Recipes.get(position);
        holder.recipeImage.setImageURI(Uri.parse("https://kedem.ru" + currRec.getRecipeImage()));
        holder.nameView.setText(currRec.getRecipeName());
        holder.themeView.setText(currRec.getRecipeTheme());
    }

    @Override
    public int getItemCount() {
        return Recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView recipeImage;
        private TextView nameView, themeView;
        ViewHolder(View view){
            super(view);
            recipeImage = (ImageView)view.findViewById(R.id.recipe_image);
            nameView = (TextView)view.findViewById(R.id.recipe_name);
            themeView = (TextView)view.findViewById(R.id.recipe_theme);
        }
    }
}

