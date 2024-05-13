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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetEmptyProducts() throws Exception {
        String response = mockMvc.perform(get("/api/v1/products")).andReturn().getResponse().getContentAsString();
        assertEquals(response, "{\"status\":200,\"data\":[],\"detail\":null}");
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        String response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},\"detail\":null}", response);
    }

    @Test
    void testCreateProductNoName() throws Exception {
        String productJson = "{\"description\": \"Desc\",\"price\": 2,\"available\": true}";
        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":500,\"data\":null,\"detail\":\"Name can't be null\"}", response);
    }

    @Test
    void testGetProducts() throws Exception {
        Product product1 = new Product("Name", "Desc", 10.0f, true, 2);
        Product product2 = new Product("Name2", "Desc2", 1f, false, 0);
        String product1Json = objectMapper.writeValueAsString(product1);
        String product2Json = objectMapper.writeValueAsString(product2);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(product1Json));
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(product2Json));
        String response = mockMvc.perform(get("/api/v1/products")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":2,\"name\":\"Name2\",\"description\":\"Desc2\",\"price\":1.0,\"available\":false,\"amountOfProduct\":0},{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testFindProductById() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/1")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},\"detail\":null}", response);
    }

    @Test
    void testFindProductByName() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true,2 );
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?name=Name")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":1,\"name\":\"Name\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product("Name", "Desc", 10.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        System.out.println(mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)).andReturn().getResponse().getContentAsString());
        Product updatedProduct = new Product(1L,"Name", "Desc new", 1000f, false, 0);
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);
        String response = mockMvc.perform(put("/api/v1/products/update_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":{\"id\":1,\"name\":\"Name\",\"description\":\"Desc new\",\"price\":1000.0,\"available\":false,\"amountOfProduct\":0},\"detail\":null}", response);
    }

    @Test
    void testSortProductASC() throws Exception {
        Product product1 = new Product("A", "Desc", 10.0f, true, 2);
        String productJson1 = objectMapper.writeValueAsString(product1);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson1))
                .andReturn().getResponse().getContentAsString();
        Product product2 = new Product("Q", "Desc", 10.0f, true, 2);
        String productJson2 = objectMapper.writeValueAsString(product2);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson2))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?sortName=ASC")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":1,\"name\":\"A\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},{\"id\":2,\"name\":\"Q\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testSortProductDESC() throws Exception {
        Product product1 = new Product("A", "Desc", 10.0f, true, 2);
        String productJson1 = objectMapper.writeValueAsString(product1);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson1))
                .andReturn().getResponse().getContentAsString();
        Product product2 = new Product("Q", "Desc", 10.0f, true, 2);
        String productJson2 = objectMapper.writeValueAsString(product2);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson2))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?sortName=DESC")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":2,\"name\":\"Q\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2},{\"id\":1,\"name\":\"A\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testFilterProductPriceGreater() throws Exception {
        Product product1 = new Product("A", "Desc", 1.0f, true, 2);
        String productJson1 = objectMapper.writeValueAsString(product1);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson1))
                .andReturn().getResponse().getContentAsString();
        Product product2 = new Product("Q", "Desc", 10.0f, true, 2);
        String productJson2 = objectMapper.writeValueAsString(product2);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson2))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?priceGreater=5")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":2,\"name\":\"Q\",\"description\":\"Desc\",\"price\":10.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testFilterProductIsAvailable() throws Exception {
        Product product1 = new Product("A", "Desc", 1.0f, true, 2);
        String productJson1 = objectMapper.writeValueAsString(product1);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson1))
                .andReturn().getResponse().getContentAsString();
        Product product2 = new Product("Q", "Desc", 10.0f, false, 0);
        String productJson2 = objectMapper.writeValueAsString(product2);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson2))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?available=true")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"data\":[{\"id\":1,\"name\":\"A\",\"description\":\"Desc\",\"price\":1.0,\"available\":true,\"amountOfProduct\":2}],\"detail\":null}", response);
    }

    @Test
    void testFilterProductFakeParameter() throws Exception {
        Product product = new Product("A", "Desc", 1.0f, true, 2);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        String response = mockMvc.perform(get("/api/v1/products/?fake=true")).andReturn().getResponse().getContentAsString();
        assertEquals("{\"status\":500,\"data\":null,\"detail\":\"Can't find filter parameter with name 'fake'.\"}", response);
    }
}
