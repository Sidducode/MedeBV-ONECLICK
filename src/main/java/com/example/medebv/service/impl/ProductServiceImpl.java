package com.example.medebv.service.impl;

import com.example.medebv.entity.Product;
import com.example.medebv.exception.ResourceNotFoundException;
import com.example.medebv.jparepository.ProductUpdateRepo;
import com.example.medebv.request.ProductUpdateRequest;
import com.example.medebv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductUpdateRepo productUpdateRepo;

    @Override
    public Product updateClient(Long id, ProductUpdateRequest request) {
        Product product = productUpdateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setProductName(request.getProductName());
        product.setActive(request.getActive());
        product.setExternalId(request.getExternalId());
        product.setEmail(request.getEmail());
        product.setDescription(request.getDescription());
        product.setDrugDbCode(request.getDrugDbCode());


        return productUpdateRepo.save(product);
    }
}
