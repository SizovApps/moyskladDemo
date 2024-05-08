package com.romashko.romashkoTestProject.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 4096;

    private String name;
    private String description;
    private Float price;
    private boolean isAvailable;

}
