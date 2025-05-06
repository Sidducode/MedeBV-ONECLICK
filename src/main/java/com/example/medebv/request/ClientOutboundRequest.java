package com.example.medebv.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientOutboundRequest {
    private String environment;
    private String clientName;
    private String dbUsername;
    private String dbPassword;
}

