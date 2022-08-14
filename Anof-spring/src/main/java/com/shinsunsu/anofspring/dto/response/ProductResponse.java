package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProductResponse {

    @Getter
    @AllArgsConstructor
    public static class productResponse {
        private Long id;
        private String name;
        private String type;
        private String brand;
        private String image;

        public productResponse(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.type = product.getType();
            this.brand = product.getBrand();
            this.image = product.getImage();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class productDetailResponse {
        private Long id;
        private String name;
        private String type;
        private String brand;
        private String rawMaterial;
        private String natrium;
        private String carbohydrates;
        private String sugar;
        private String fat;
        private String trans_fat;
        private String saturated_fat;
        private String cholesterol;
        private String protein;
        private String calorie;
        private String capacity;
        private String image;
        private String barcode;

        public productDetailResponse(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.type = product.getType();
            this.brand = product.getBrand();
            this.rawMaterial = product.getRawMaterial();
            this.natrium = product.getNatrium();
            this.carbohydrates = product.getCarbohydrates();
            this.sugar = product.getSugar();
            this.fat = product.getFat();
            this.trans_fat = product.getTrans_fat();
            this.saturated_fat = product.getSaturated_fat();
            this.cholesterol = product.getCholesterol();
            this.protein = product.getProtein();
            this.calorie = product.getCalorie();
            this.capacity = product.getCapacity();
            this.image = product.getImage();
            this.barcode = product.getBarcode();
        }
    }
}
