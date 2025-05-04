package com.example.medebv.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductUpdateRequest {

    private String productName;

    private String active;

    private String externalId;

    private String email;

    private String description;

    private String drugDbCode;
}
