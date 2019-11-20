package com.shveed.cookmegood.pdf_parser;

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
}
