package com.shinsunsu.anofspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductDto {
    String name;
    String brand;
    String image;

    public SearchProductDto(String name, String brand, String image) {
        this.name = name;
        this.brand = brand;
        this.image = image;
    }
}
