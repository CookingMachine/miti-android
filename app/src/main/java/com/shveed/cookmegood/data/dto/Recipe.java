package com.shveed.cookmegood.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    private String name;

    private String description;

    private String time;

    private int image;

    private String kitchen;

    public Recipe(String name, String kitchen, int image){
        this.name = name;
        this.kitchen = kitchen;
        this.image = image;
    }
}
