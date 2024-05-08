package com.romashko.romashkoTestProject.models;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Product {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 4096;

    @NonNull
    private String name;
    @Builder.Default
    private String description = "";
    @Builder.Default
    private Float price = 0f;
    @Builder.Default
    private boolean isAvailable = true;

    public static Product createProduct(String name, String description, Float price, boolean isAvailable) {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .isAvailable(isAvailable)
                .build();
    }

}
