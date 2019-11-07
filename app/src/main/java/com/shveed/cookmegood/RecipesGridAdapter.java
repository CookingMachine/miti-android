package com.shveed.cookmegood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shveed.wallpapperparser.R;

public class RecipesGridAdapter extends BaseAdapter {
    private Context mContext;

    private String[] data = {"Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки"};

    private String amount = "101 позиция";

    public RecipesGridAdapter(Context c){
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position){
        return data[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        if (convertView == null) {
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.grid_recipe_element, parent, false);
        } else {
            grid = (View) convertView;
        }

        TextView ingredText = (TextView) grid.findViewById(R.id.recipeName);
        TextView ingredAmount = (TextView) grid.findViewById(R.id.recipeAmount);
        ingredText.setText(data[position]);
        ingredAmount.setText(amount);

        return grid;
    }
}
