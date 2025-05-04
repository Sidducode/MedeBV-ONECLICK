package com.example.medebv.jparepository;

import com.example.medebv.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUpdateRepo extends JpaRepository<Product, Long> {

}
