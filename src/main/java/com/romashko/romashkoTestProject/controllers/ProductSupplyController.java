package com.romashko.romashkoTestProject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.romashko.romashkoTestProject.models.ProductSupply;
import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.responses.ProductsServiceResponse;
import com.romashko.romashkoTestProject.serializers.ProductDeserializer;
import com.romashko.romashkoTestProject.services.ProductSupplyService;
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
@RequestMapping("/api/v1/product_supply")
public class ProductSupplyController {


    private final ProductSupplyService productSupplyService;
    private final ProductsService productsService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductSupplyController(ProductSupplyService productSupplyService, ProductsService productsService,  ObjectMapper objectMapper) {
        this.productSupplyService = productSupplyService;
        this.productsService = productsService;
        this.objectMapper = objectMapper;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer(productsService));
        this.objectMapper.registerModule(module);
    }

    @GetMapping
    public ResponseEntity<ProductsServiceResponse> getAllProductSupplies() {
        List<ProductSupply> allProductSupplies = productSupplyService.getAllProductSupplies();
        return new ResponseEntity<>(new ProductsServiceResponse(200, allProductSupplies, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductsServiceResponse> createProductSupply(@RequestBody String productSupplyJson) {
        try {
            ProductSupply productSupply = objectMapper.readValue(productSupplyJson, ProductSupply.class);
            ProductSupply newProductSupply = productSupplyService.createProductSupply(productSupply);
            return new ResponseEntity<>(new ProductsServiceResponse(200, newProductSupply, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductsServiceResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsServiceResponse> findProductSupplyById(@PathVariable Long id) {
        ProductSupply productSupply = productSupplyService.findProductSupplyById(id);
        return new ResponseEntity<>(new ProductsServiceResponse(200, productSupply, null), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductsServiceResponse> updateProductSupply(@RequestBody String productSupplyJson) {
        try {
            ProductSupply productSupply = objectMapper.readValue(productSupplyJson, ProductSupply.class);
            ProductSupply updateProductSupply = productSupplyService.updateProductSupply(productSupply);
            return new ResponseEntity<>(new ProductsServiceResponse(200, updateProductSupply, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductsServiceResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ProductsServiceResponse> deleteProductSupplyById(@PathVariable Long id) {
        productSupplyService.deleteProductSupplyById(id);
        return new ResponseEntity<>(new ProductsServiceResponse(200, null, null), HttpStatus.OK);
    }

}
