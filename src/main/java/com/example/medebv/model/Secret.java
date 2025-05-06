package com.example.medebv.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Secret {
    private String requestUrl;
    private String clientId;
    private String username;
    private String password;
}

