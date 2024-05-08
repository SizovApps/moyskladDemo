package com.romashko.romashkoTestProject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.romashko.romashkoTestProject.models.Product;
import java.io.IOException;
import java.text.MessageFormat;

public class ProductDeserializer extends StdDeserializer<Product> {

    public ProductDeserializer() {
        this(null);
    }

    public ProductDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, NullPointerException {
        JsonNode node = jp.getCodec().readTree(jp);
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

        boolean isAvailable = node.has("isAvailable") && node.get("isAvailable").booleanValue();

        return new Product(name, description, price, isAvailable);
    }
}