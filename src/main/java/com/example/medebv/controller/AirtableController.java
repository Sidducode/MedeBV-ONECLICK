package com.example.medebv.controller;

import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.response.MedeBVResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medebv.request.MedebvRequest;
import com.example.medebv.service.AirtableService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/airtable")
@RequiredArgsConstructor
public class AirtableController {
	 private static final Logger LOGGER = LogManager.getLogger(AirtableController.class);
	private static final String SCRIPTS_GENERATION_AND_PR_CREATION_DONE_SUCCESSFULLY = "Scripts generation and PR creation done successfully";
	
	@Autowired
	private  AirtableService airtableService;
	@GetMapping("/getdetails")
    public ResponseEntity<Object> getAll(@RequestBody MedebvRequest requets) throws MEDEBVCustomException {
		LOGGER.info("Started Scripts");
        MedeBVResponse resp= airtableService.getAllRecords(requets);
		if (!resp.getErrorResponseList().isEmpty())
			return new ResponseEntity<>(resp.getErrorResponseList(), HttpStatus.BAD_REQUEST);
		LOGGER.info(SCRIPTS_GENERATION_AND_PR_CREATION_DONE_SUCCESSFULLY);
		List<String > responseList = new ArrayList<>();
		responseList.add(SCRIPTS_GENERATION_AND_PR_CREATION_DONE_SUCCESSFULLY);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
 
