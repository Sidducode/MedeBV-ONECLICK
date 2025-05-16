package com.example.medebv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fields {

    @JsonProperty("PRODUCTID")
    private String productId;

    @JsonProperty("PRODUCTNAME")
    private String productName;

    @JsonProperty("ACTIVE")
    private String active;

    @JsonProperty("EXTERNALID")
    private String externalId;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("DRUG_DB_CODE")
    private String drugDBCode;

    @JsonProperty("DESCRIPTION")
    private String description;

    @JsonProperty("ADMINCODEID")
    private String adminCodeId;

    @JsonProperty("EXTERNALCODE")
    private String externalCode;

    @JsonProperty("EMAIL_ID")
    private String emailId;

    @JsonProperty("EMAIL_NAME")
    private String emailName;

    @JsonProperty("EMAIL_TEMPLATE")
    private String emailTemplate;

    @JsonProperty("EMAIL_ATTACHMENTS")
    private String emailAttachments;

    @JsonProperty("NCLIENTID")
    private String nclientId;

    @JsonProperty("USERNAME")
    private String username;

    @JsonProperty("CLIENT_ID")
    private String clientId;

    @JsonProperty("PASSWORD")
    private String password;

    @JsonProperty("REQEST_Url")
    private String requestUrl;

    @JsonProperty("DISPLAYORDER")
    private String displayOrder;


}
