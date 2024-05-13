package com.romashko.romashkoTestProject.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductsServiceResponse {
    private int status;
    private Object data;
    private String detail;
}
