package com.shveed.cookmegood;

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

    private String image;

    private String kitchen;

    public Recipe(String name, String kitchen){
        this.name = name;
        this.kitchen = kitchen;
    }
}
