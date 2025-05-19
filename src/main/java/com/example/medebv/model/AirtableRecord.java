package com.example.medebv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AirtableRecord {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdTime")
    private String createdTime;

    private Fields fields;
}
