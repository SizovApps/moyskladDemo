package com.romashko.romashkoTestProject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.io.IOException;
import java.text.MessageFormat;

public class ProductDeserializer extends StdDeserializer<Product> {


    private final ProductsService productsService;

    public ProductDeserializer(ProductsService productsService) {
        this(null, productsService);
    }

    public ProductDeserializer(Class<?> vc, ProductsService productsService) {
        super(vc);
        this.productsService = productsService;
    }

    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, NullPointerException {
        JsonNode node = jp.getCodec().readTree(jp);

        boolean isUpdate = false;
        try {
            isUpdate = (boolean) ctxt.findInjectableValue("isUpdate", null, null);
        } catch (InvalidDefinitionException ignored) { }

        if (node.has("id") && !node.get("id").isNull() && !isUpdate) {
            Product product = productsService.findProductById(node.get("id").longValue());
            if (product == null) {
                throw new IllegalArgumentException(String.format("Can't find product with id = %d.", node.get("id").longValue()));
            }
            return product;
        }
        if (!node.has("name")) {
            throw new IllegalArgumentException("Name can't be null");
        }
        String name = node.get("name").asText();
        if (name.length() > Product.NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(MessageFormat.format("Product name must be shorter than {0} symbols", Product.NAME_MAX_LENGTH));
        }

        String description = node.get("description").asText();
        if (description.length() > Product.DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException(MessageFormat.format("Product description must be shorter than {0} symbols", Product.DESCRIPTION_MAX_LENGTH));
        }

        float price = node.has("price") ? node.get("price").floatValue() : 0f;
        if (price < 0) {
            throw new IllegalArgumentException("Product price can't be less than 0");
        }

        boolean available = node.has("available") && node.get("available").booleanValue();

        int amountOfProduct = node.has("amountOfProduct") ? node.get("amountOfProduct").intValue() : available ? 1 : 0;
        if (amountOfProduct < 0) {
            throw new IllegalArgumentException("AmountOfProduct price can't be less than 0");
        }

        if (node.has("id")) {
            return new Product(node.get("id").longValue(), name, description, price, available, amountOfProduct);
        } else {
            return new Product(name, description, price, available, amountOfProduct);
        }
    }
}