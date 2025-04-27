package com.example.medebv.service.impl;

import com.example.medebv.request.MedebvRequest;
import com.example.medebv.service.AirtableService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AirtableServiceImpl implements AirtableService {
	public static final String DEFAULT_SCHEMA_UPDATE_FOLDER_NAME = "999-schema-update";
    private static final Logger LOGGER = LogManager.getLogger(AirtableServiceImpl.class);
    
	@Autowired
	private RestTemplate restTemplate;

    private  AirtableService airtableService;
    
   private MedebvRequest requets;
    
    @Value("${airtable.table.url}")
    private String airTableUrl;
    
//    @Value("${airtable.table.baseid}")
//    private String baseId;
//    
//    @Value("${airtable.tablename}")
//    private String tableName;
    
    @Value("${airtable.token}")
    private String airtabletoken;
    

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + airtabletoken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
	public ResponseEntity<String> getAllRecords(MedebvRequest requets) {
        String url = airTableUrl + "/" + requets.getBaseId() + "/" + requets.getBaseName();

        System.out.println("get all data int airtable");

        //  ResponseEntity<String> productList = airtableService.getAllRecords();

		 return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), String.class);
		 
	}
	
}
