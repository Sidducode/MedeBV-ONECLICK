package com.example.medebv.jparepository;

import com.example.medebv.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirtableRepository extends JpaRepository<Product, Long> {
   
}
