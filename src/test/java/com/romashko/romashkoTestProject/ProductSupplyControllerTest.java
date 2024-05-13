package com.romashko.romashkoTestProject;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.romashko.romashkoTestProject.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductSupplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetEmptyProductSupplies() throws Exception {
        String response = mockMvc.perform(get("/api/v1/product_supply")).andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":[],\"detail\":null}");
    }

    @Test
    void testCreateProductSupply() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson));
        String productSupplyJson = "{\"name\": \"first good supply 1\",\"product\":{\"id\": 1},\"productAmount\": 2}";
        String response = mockMvc.perform(post("/api/v1/product_supply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productSupplyJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"first good supply 1\",\"product\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},\"productAmount\":2},\"detail\":null}", response);
    }

    @Test
    void testGetProductSupplyById() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson));
        String productSupplyJson = "{\"name\": \"first good supply 1\",\"product\":{\"id\": 1},\"productAmount\": 2}";
        mockMvc.perform(post("/api/v1/product_supply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productSupplyJson));
        String response =  mockMvc.perform(get("/api/v1/product_supply/1"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"first good supply 1\",\"product\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},\"productAmount\":2},\"detail\":null}", response);
    }

    @Test
    void testUpdateProductSupply() throws Exception {
            Product product = new Product("Name", "Desc", 10.0f, true, 2);
            String productJson = objectMapper.writeValueAsString(product);
            mockMvc.perform(post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productJson));
            String productSupplyJson = "{\"name\": \"first good supply 1\",\"product\":{\"id\": 1},\"productAmount\": 2}";
            mockMvc.perform(post("/api/v1/product_supply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productSupplyJson));
            String updatedProductSupplyJson = "{\"id\":1,\"name\": \"updated name!\",\"product\":{\"id\": 1},\"productAmount\": 1}";
            String response = mockMvc.perform(put("/api/v1/product_supply/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedProductSupplyJson))
                    .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"updated name!\",\"product\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},\"productAmount\":1},\"detail\":null}", response);
    }

    @Test
    void testDeleteProductSupply() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson));
        String productSupplyJson = "{\"name\": \"first good supply 1\",\"product\":{\"id\": 1},\"productAmount\": 2}";
        mockMvc.perform(post("/api/v1/product_supply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productSupplyJson));
        String response = mockMvc.perform(delete("/api/v1/product_supply/1/delete")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":null,\"detail\":null}", response);
    }
}
