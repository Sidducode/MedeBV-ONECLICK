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
public class Table {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("primaryFieldId")
    private String primaryFieldId;

    @JsonProperty("fields")
    private List<Fields> fields;

    @JsonProperty("views")
    private List<View> views;

}
