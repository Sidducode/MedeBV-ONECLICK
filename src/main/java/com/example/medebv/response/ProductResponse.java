package com.example.medebv.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ProductResponse {
    private List<String> errorResponseList;
    private HttpStatus httpStatus;
}
