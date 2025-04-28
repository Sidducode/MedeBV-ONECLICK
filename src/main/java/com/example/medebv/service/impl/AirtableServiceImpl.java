package com.example.medebv.service.impl;

import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.model.AirtableBaseResponse;
import com.example.medebv.model.Base;
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
    
    @Value("${airtable.list.table.url}")
    private String airTableUrl;

    @Value("${airtable.base.url}")
    private String airTableBaseUrl;

    @Value("${airtable.token}")
    private String airtabletoken;
    

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + airtabletoken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
	public ResponseEntity<String> getAllRecords(MedebvRequest requets) throws MEDEBVCustomException{
        String airtablebaseName = requets.getBaseName();
        String baseId= getBaseIDFromBaseName(airtablebaseName);
       String url = airTableUrl.replace("baseId", baseId);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), String.class);
        if (response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No records found in Airtable.");
        } else {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), String.class);

        }
    }

    public String getBaseIDFromBaseName(String baseName) throws MEDEBVCustomException {
        String baseurl = airTableBaseUrl;
        HttpHeaders headers = headers();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<AirtableBaseResponse> response = restTemplate.exchange(baseurl, HttpMethod.GET, entity, AirtableBaseResponse.class);

            if (response.getBody() == null) {
                throw new MEDEBVCustomException("No bases found in Airtable.", HttpStatus.NOT_FOUND);
            }
            for (Base base : response.getBody().getBases()) {
                if (base.getName().equalsIgnoreCase(baseName)) {
                    return base.getId();
                }
            }
            throw new MEDEBVCustomException("Base name not found: " + baseName, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error while fetching base ID: " + e.getMessage(),e);
            throw new MEDEBVCustomException("Error while fetching base ID: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
