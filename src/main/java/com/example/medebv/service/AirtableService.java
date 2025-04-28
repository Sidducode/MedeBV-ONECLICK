package com.example.medebv.service;

import com.example.medebv.exception.MEDEBVCustomException;
import org.springframework.http.ResponseEntity;

import com.example.medebv.request.MedebvRequest;

public interface AirtableService {

	
	ResponseEntity<String> getAllRecords( MedebvRequest requets) throws MEDEBVCustomException;
}
