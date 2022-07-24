package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    Long id;
    String name;
    String type;
    String brand;
    String rawMaterial;
    String natrium;
    String carbohydrates;
    String sugar;
    String fat;
    String trans_fat;
    String saturated_fat;
    String cholesterol;
    String protein;
    String calorie;
    String capacity;
    String image;
    String barcode;

    public static ProductResponse productResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .image(product.getImage())
                .build();
    }

    public static ProductResponse productDetailResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .type(product.getType())
                .brand(product.getBrand())
                .rawMaterial(product.getRawMaterial())
                .natrium(product.getNatrium())
                .carbohydrates(product.getCarbohydrates())
                .sugar(product.getSugar())
                .fat(product.getFat())
                .trans_fat(product.getTrans_fat())
                .saturated_fat(product.getSaturated_fat())
                .cholesterol(product.getCholesterol())
                .protein(product.getProtein())
                .calorie(product.getCalorie())
                .capacity(product.getCapacity())
                .image(product.getImage())
                .barcode(product.getBarcode())
                .build();
    }
}
