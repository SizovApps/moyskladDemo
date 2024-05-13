package com.romashko.romashkoTestProject.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 4096;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String description;

    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private Float price;
    private boolean available;


    public Product(String name, String description, float price, boolean available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }
}
