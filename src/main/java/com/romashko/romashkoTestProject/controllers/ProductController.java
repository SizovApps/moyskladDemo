package com.romashko.romashkoTestProject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.responses.ProductResponse;
import com.romashko.romashkoTestProject.serializers.ProductDeserializer;
import com.romashko.romashkoTestProject.services.ProductService;
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

    private final ProductService productService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        this.objectMapper.registerModule(module);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(new ProductResponse(200, allProducts, null), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> findProductByName(@PathVariable String name) {
        Product product = productService.findProductByName(name);
        return new ResponseEntity<>(new ProductResponse(200, product, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody String productJson) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            productService.createProduct(product);
            return new ResponseEntity<>(new ProductResponse(200, product, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_product")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody String productJson) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product updated_product = productService.updateProduct(product);
            return new ResponseEntity<>(new ProductResponse(200, updated_product, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{name}/delete")
    public ResponseEntity<ProductResponse> deleteProductByName(@PathVariable String name) {
        productService.deleteProductByName(name);
        return new ResponseEntity<>(new ProductResponse(200, null, null), HttpStatus.OK);
    }
}
