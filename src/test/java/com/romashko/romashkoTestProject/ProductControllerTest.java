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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testGetEmptyProducts() throws Exception {
        String response = mockMvc.perform(get("/api/v1/products")).andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":[],\"detail\":null}");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testCreateProduct() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true);
        String productJson = objectMapper.writeValueAsString(product);
        String response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":{\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":false},\"detail\":null}");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testCreateProductNoName() throws Exception {
        String productJson = "{\"description\": \"Desc\",\"price\": 2,\"isAvailable\": true}";
        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":500,\"data\":null,\"detail\":\"Name can't be null\"}");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testGetProducts() throws Exception {
        Product product1 = new Product("Name", "Desc", 10.0f, true);
        Product product2 = new Product("Name2", "Desc2", 1f, false);
        String product1Json = objectMapper.writeValueAsString(product1);
        String product2Json = objectMapper.writeValueAsString(product2);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(product1Json));
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(product2Json));
        String response = mockMvc.perform(get("/api/v1/products")).andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":[{\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":false},{\"name\":\"Name2\",\"description\":\"Desc2\",\"price\":1.0,\"available\":false}],\"detail\":null}");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testFindProductByName() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/Name")).andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":{\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":false},\"detail\":null}");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void testUpdateProduct() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson));
        Product updatedProduct = new Product("Name", "Desc new", 1000f, false);
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);
        String response = mockMvc.perform(put("/api/v1/products/update_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":{\"name\":\"Name\",\"description\":\"Desc new\",\"price\":1000.0,\"available\":false},\"detail\":null}");
    }
}
