package com.example.medebv.controller;

import com.example.medebv.entity.Product;
import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.request.ProductUpdateRequest;
import com.example.medebv.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductUpdateController {
    private static final Logger LOGGER = LogManager.getLogger(ProductUpdateController.class);

    @Autowired
    private ProductService productService;

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateClient (@PathVariable Long id,@RequestBody ProductUpdateRequest request) throws MEDEBVCustomException {
        Product updated = productService.updateClient(id, request);
        return ResponseEntity.ok(updated);
    }
}
