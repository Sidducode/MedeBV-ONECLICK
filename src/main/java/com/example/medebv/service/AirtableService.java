package com.example.medebv.service;

import org.springframework.http.ResponseEntity;

import com.example.medebv.request.MedebvRequest;

public interface AirtableService {

	
	ResponseEntity<String> getAllRecords( MedebvRequest requets);
}
