package com.example.medebv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCTID")
    private Integer productId;

    @Column(name = "PRODUCTNAME")
    private String productName;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "EXTERNALID")
    private String externalId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DRUG_DB_CODE")
    private String drugDbCode;


}

