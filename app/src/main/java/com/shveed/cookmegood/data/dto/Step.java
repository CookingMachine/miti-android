package com.shveed.cookmegood.data.dto;

import lombok.Data;

@Data
public class Step {

    private int stepNumber;

    private String description;

    private String title;
    /*
    * @drawable resource of int
    */
    private int imgResource;

    public Step(int stepNumber, String description, String title) {
        this.stepNumber = stepNumber;
        this.description = description;
        this.title = title;
    }
}
