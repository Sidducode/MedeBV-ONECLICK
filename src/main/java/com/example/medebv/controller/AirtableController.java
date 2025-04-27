package com.example.medebv.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medebv.request.MedebvRequest;
import com.example.medebv.service.AirtableService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/airtable")
@RequiredArgsConstructor
public class AirtableController {
	 private static final Logger LOGGER = LogManager.getLogger(AirtableController.class);
	
	
	@Autowired
	private  AirtableService airtableService;
	@GetMapping("/getdetails")
    public ResponseEntity<String> getAll(@RequestBody MedebvRequest requets) {
		LOGGER.info("Started Scripts");
        return airtableService.getAllRecords(requets);
    }
}
 
