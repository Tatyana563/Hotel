package com.hotel.model.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
@RequiredArgsConstructor
@Data
public class Country {
    private String country;
    private Set<String> cities;
}
