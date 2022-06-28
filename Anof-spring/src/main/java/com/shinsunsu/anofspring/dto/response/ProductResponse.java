package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    String name;
    String brand;
    String image;

    public static ProductResponse productResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .image(product.getImage())
                .build();
    }
}
