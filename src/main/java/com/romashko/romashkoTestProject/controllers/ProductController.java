package com.romashko.romashkoTestProject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.responses.ProductResponse;
import com.romashko.romashkoTestProject.serializers.ProductDeserializer;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductsService productsService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductsService productsService, ObjectMapper objectMapper) {
        this.productsService = productsService;
        this.objectMapper = objectMapper;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        this.objectMapper.registerModule(module);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts() {
        List<Product> allProducts = productsService.getAllProducts();
        return new ResponseEntity<>(new ProductResponse(200, allProducts, null), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> findProductByName(@PathVariable String name) {
        Product product = productsService.findProductByName(name);
        return new ResponseEntity<>(new ProductResponse(200, product, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody String productJson) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            productsService.createProduct(product);
            return new ResponseEntity<>(new ProductResponse(200, product, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_product")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody String productJson) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product updated_product = productsService.updateProduct(product);
            return new ResponseEntity<>(new ProductResponse(200, updated_product, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{name}/delete")
    public ResponseEntity<ProductResponse> deleteProductByName(@PathVariable String name) {
        productsService.deleteProductByName(name);
        return new ResponseEntity<>(new ProductResponse(200, null, null), HttpStatus.OK);
    }
}
