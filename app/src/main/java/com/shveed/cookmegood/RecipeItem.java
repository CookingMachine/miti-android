package com.shveed.cookmegood;

import android.media.Image;

public class RecipeItem {
    private String recipeTheme;
    private String recipeName;
    private String recipeImage;

    public RecipeItem(String theme, String name, String image){
        this.recipeTheme = theme;
        this.recipeName = name;
        this.recipeImage = image;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeTheme() {
        return recipeTheme;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeTheme(String recipeTheme) {
        this.recipeTheme = recipeTheme;
    }
}
