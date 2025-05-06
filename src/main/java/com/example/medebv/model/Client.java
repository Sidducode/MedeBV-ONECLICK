package com.example.medebv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Client {
    private String name;
    private List<Secret> secrets;
}
