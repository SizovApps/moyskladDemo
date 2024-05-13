package com.romashko.romashkoTestProject.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.models.ProductSell;
import com.romashko.romashkoTestProject.responses.ProductsServiceResponse;
import com.romashko.romashkoTestProject.serializers.ProductDeserializer;
import com.romashko.romashkoTestProject.services.ProductSellService;
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
@RequestMapping("/api/v1/product_sell")
public class ProductSellController {

    private final ProductSellService productSellService;
    private final ProductsService productsService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductSellController(ProductSellService productSellService, ProductsService productsService,  ObjectMapper objectMapper) {
        this.productSellService = productSellService;
        this.productsService = productsService;
        this.objectMapper = objectMapper;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer(productsService));
        this.objectMapper.registerModule(module);
    }

    @GetMapping
    public ResponseEntity<ProductsServiceResponse> getAllProductSells() {
        List<ProductSell> allProductSells = productSellService.getAllProductSells();
        return new ResponseEntity<>(new ProductsServiceResponse(200, allProductSells, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductsServiceResponse> createProductSell(@RequestBody String productSupplyJson) {
        try {
            ProductSell productSell = objectMapper.readValue(productSupplyJson, ProductSell.class);
            ProductSell newProductSell = productSellService.createProductSell(productSell);
            return new ResponseEntity<>(new ProductsServiceResponse(200, newProductSell, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductsServiceResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsServiceResponse> findProductSellById(@PathVariable Long id) {
        ProductSell productSell = productSellService.findProductSellById(id);
        return new ResponseEntity<>(new ProductsServiceResponse(200, productSell, null), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductsServiceResponse> updateProductSell(@RequestBody String productSupplyJson) {
        try {
            ProductSell productSell = objectMapper.readValue(productSupplyJson, ProductSell.class);
            ProductSell updateProductSell = productSellService.updateProductSell(productSell);
            return new ResponseEntity<>(new ProductsServiceResponse(200, updateProductSell, null), HttpStatus.OK);
        } catch (JsonProcessingException | IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ProductsServiceResponse(500, null, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ProductsServiceResponse> deleteProductSellById(@PathVariable Long id) {
        productSellService.deleteProductSellById(id);
        return new ResponseEntity<>(new ProductsServiceResponse(200, null, null), HttpStatus.OK);
    }
}
