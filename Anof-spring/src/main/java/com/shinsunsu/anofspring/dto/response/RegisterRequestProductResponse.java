package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import com.shinsunsu.anofspring.domain.User;
import lombok.Getter;

@Getter
public class RegisterRequestProductResponse {

    private String name;
    private String brand;
    private String product_image;
    private String barcode;
    private int enable;
    private Long userId;
    private String userNickname;

    public RegisterRequestProductResponse(RegisterProduct registerProduct) {
        this.name = registerProduct.getName();
        this.brand = registerProduct.getBrand();
        this.product_image = registerProduct.getProduct_image();
        this.barcode = registerProduct.getBarcode();
        this.enable = registerProduct.getEnable();
        this.userId = registerProduct.getUser().getId();
        this.userNickname = registerProduct.getUser().getNickname();
    }


}
