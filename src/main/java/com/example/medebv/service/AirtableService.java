package com.example.medebv.service;

import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.response.MedeBVResponse;
import org.springframework.http.ResponseEntity;

import com.example.medebv.request.MedebvRequest;

public interface AirtableService {


	MedeBVResponse getAllRecords(MedebvRequest requets) throws MEDEBVCustomException;
}
