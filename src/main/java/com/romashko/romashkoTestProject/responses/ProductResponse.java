package com.romashko.romashkoTestProject.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private int status;
    private Object data;
    private String detail;
}
