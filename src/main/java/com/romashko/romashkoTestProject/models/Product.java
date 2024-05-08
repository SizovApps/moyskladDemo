package com.romashko.romashkoTestProject.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@Entity
@Table(name = "products")
public class Product {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 4096;

    @Id
    @GeneratedValue
    private Long id;
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

    public Product() {
        this.id = 0L;
        this.name = "";
        this.description = "";
        this.price = 0f;
        this.isAvailable = true;
    }

}
