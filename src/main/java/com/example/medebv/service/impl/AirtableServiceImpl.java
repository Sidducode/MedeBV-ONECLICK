package com.example.medebv.service.impl;

import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.model.*;
import com.example.medebv.model.AirtableRecord;
import com.example.medebv.request.MedebvRequest;
import com.example.medebv.response.MedeBVResponse;
import com.example.medebv.service.AirtableService;
import com.example.medebv.utility.FieldConstants;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
    private String allairTableUrl;

    @Value("${airtable.base.url}")
    private String airTableBaseUrl;

    @Value("${airtable.table.url}")
    private String airTableUrl;

    @Value("${airtable.token}")
    private String airtabletoken;

    @Value("${replicated.folder}")
    private String replicatedFolder;

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + airtabletoken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
	public MedeBVResponse getAllRecords(MedebvRequest requets) throws MEDEBVCustomException {
        String airtablebaseName = requets.getBaseName();
        String baseId = getBaseIDFromBaseName(airtablebaseName);
         allairTableUrl = allairTableUrl.replace("baseId", baseId);
        List<String> errorResponseList = new ArrayList<>();

        List<Table> tableList = new ArrayList<>();

        ResponseEntity<AirTablesResponse> response = restTemplate.exchange(allairTableUrl, HttpMethod.GET, new HttpEntity<>(headers()), AirTablesResponse.class);
        if (response.getBody() == null) {
            errorResponseList.add("Response body is null");
        }
        tableList = response.getBody().getTables();
        if (tableList.isEmpty()) {
            errorResponseList.add("No table data found for the given Airtable baseId");
            return new MedeBVResponse(errorResponseList, HttpStatus.BAD_REQUEST);
        }
        String url =airTableUrl + baseId +FieldConstants.STRING_FILE_SEPARATOR;
        try {
            FileWriter writer = new FileWriter(replicatedFolder + FieldConstants.DEPLOYMENT_DATA_SQL, false); // false = overwrite
            writer.write(""); // Clears content
            writer.close();
            LOGGER.info("Cleared existing SQL script file.");
        } catch (IOException e) {
            LOGGER.error("Failed to clear SQL file", e);
            throw new MEDEBVCustomException("Unable to initialize SQL file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        readAirtableData(tableList,url);
        return new MedeBVResponse(errorResponseList, HttpStatus.OK);
    }
public void readAirtableData(List<Table> tableList, String url) throws MEDEBVCustomException {
        List<String> errorResponseList = new ArrayList<>();
        for (Table table : tableList) {
            String tableName = table.getName();
            switch (tableName){
                case "PRODUCT":
                    getAllProductDetails(url+tableName);
                    break;
                case "ADMIN_CODES":
                     getAllAdminCodesDetails(url+tableName);
                    break;
                default:
                    break;
            }
        }
    }

    private void getAllProductDetails(String url) throws MEDEBVCustomException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("--- PRODUCT TABLE DATA ---\n");
        List<AirtableRecord> productRecordList = getRecordsFromAirTable(url);

        productRecordList.forEach(tableData -> {
            Fields fields = tableData.getFields();
            if (fields != null) {

                    String sql = String.format(
                            "MERGE INTO PRODUCT pr USING dual "
                                    + " ON (pr.PRODUCTNAME = '%s') WHEN NOT MATCHED THEN "
                                    + " INSERT (PRODUCTID,PRODUCTNAME,EXTERNALID,ACTIVE, DRUG_DB_CODE,EMAIL,DESCRIPTION) VALUES "
                                    + "(PRODUCTSEQUENCE.nextval,'%s', '%s', '%s', '%s',%s, %s);",
                            fields.getProductName(),fields.getProductName(), fields.getExternalId(),
                            fields.getActive(), fields.getDrugDBCode(),fields.getEmail(), fields.getDescription());

                    sqlBuilder.append(sql).append("\n");
                }

        });
        sqlBuilder.append("\n");

        try (FileWriter writer = new FileWriter(replicatedFolder + FieldConstants.DEPLOYMENT_DATA_SQL,true)) {
            writer.write(sqlBuilder.toString());
            LOGGER.info("SQL queries saved successfully to product.sql");
        } catch (IOException e) {
            LOGGER.error(FieldConstants.ERROR_SAVING_SQL_FILE, e.getMessage(), e);
            throw new MEDEBVCustomException("Error saving SQL file:" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void getAllAdminCodesDetails(String url) throws MEDEBVCustomException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("--- ADMIN_CODES TABLE DATA ---\n");
        List<AirtableRecord> productRecordList = getRecordsFromAirTable(url);
        productRecordList.forEach(tableData -> {
            Fields fields = tableData.getFields();
            if (fields != null) {
                String sql = String.format(
                        "MERGE INTO ADMIN_CODES pr USING dual "
                                + " ON (pr.EXTERNALCODE = '%s') WHEN NOT MATCHED THEN "
                                + " INSERT (ADMINCODEID,PRODUCTNAME,EXTERNALID,DISPLAYORDER) VALUES "
                                + "(ADMINCODESEQUENCE.nextval,'%s', '%s', '%s');",
                        fields.getExternalCode(),fields.getProductName(),fields.getExternalCode(),
                        fields.getDisplayOrder());
                sqlBuilder.append(sql).append("\n");
            }
        });
        sqlBuilder.append("\n");

        try (FileWriter writer = new FileWriter(replicatedFolder + FieldConstants.DEPLOYMENT_DATA_SQL,true)) {
            writer.write(sqlBuilder.toString());
            LOGGER.info("SQL queries saved successfully to ADMINCODES.sql");
        } catch (IOException e) {
            LOGGER.error(FieldConstants.ERROR_SAVING_SQL_FILE, e.getMessage(), e);
            throw new MEDEBVCustomException("Error saving SQL file:" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public List<AirtableRecord> getRecordsFromAirTable(String url) throws MEDEBVCustomException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer patDENjBsu7mvOmQs.cb1cf8b41de5b7f3f0985c34adcbb47d5526cb71852c82666fcc253b1a835c59");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try{
            ResponseEntity<AirtableResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, AirtableResponse.class);
            return response.getBody()!= null ? response.getBody().getRecords() : List.of();

        } catch (Exception e) {
            LOGGER.error("Error while fetching records: " + e.getMessage(),e);
            throw new MEDEBVCustomException("Error while fetching records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
