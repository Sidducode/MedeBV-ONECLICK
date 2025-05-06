package com.example.medebv.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_outbound_details")
public class ClientBoundDetails {
    @Id
    @Column(name = "ID", nullable = false, length = 50)
    private String id;

    @Column(name = "NCLIENTID")
    private String nClientId;

    @Column(name = "AUTH_URL")
    private String authUrl;

    @Column(name = "REQUEST_URL")
    private String requestUrl;

    @Column(name = "VERSION_URL")
    private String versionUrl;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AUDIENCE_ID")
    private String audienceId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "AUTH_TYPE")
    private String authType;

    @Column(name = "ADDITIONAL_PROPERTY", columnDefinition = "TEXT")
    private String additionalProperty;

    @Column(name = "MESSAGE_TYPE")
    private String messageType;

    @Column(name = "SALT_URL")
    private String saltUrl;

    @Column(name = "RSA_KEY_VALUE", columnDefinition = "TEXT")
    private String rsaKeyValue;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "REDIRECT_URL")
    private String redirectUrl;
}

