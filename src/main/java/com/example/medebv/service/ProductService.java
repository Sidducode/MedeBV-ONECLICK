package com.example.medebv.service;

import com.example.medebv.entity.Product;
import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.request.ProductUpdateRequest;


public interface ProductService {

    Product updateClient(Long id, ProductUpdateRequest request) throws MEDEBVCustomException;
}
