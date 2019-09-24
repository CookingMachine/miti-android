package com.shveed.cookmegood;

import android.media.Image;

public class RecipeItem {
    private String recipeTheme;
    private String recipeName;
    private String recipeHref;

    public RecipeItem(String theme, String name, String href){
        this.recipeTheme = theme;
        this.recipeName = name;
        this.recipeHref = href;
    }

    public String getRecipeHref() {
        return recipeHref;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeTheme() {
        return recipeTheme;
    }

    public void setRecipeHref(String href) {
        this.recipeHref = href;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeTheme(String recipeTheme) {
        this.recipeTheme = recipeTheme;
    }
}
