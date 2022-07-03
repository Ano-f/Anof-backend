package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import com.shinsunsu.anofspring.domain.User;
import lombok.Getter;

@Getter
public class RegisterProductRequest {

    private String name;
    private String brand;
    private String product_image;
    private String info_image;
    private String barcode;
    private int enable;
    private User user;

    public static RegisterProduct RegisterProduct(RegisterProductRequest request, User user) {
        RegisterProduct registerProduct = new RegisterProduct();
        registerProduct.setName(request.getName());
        registerProduct.setBrand(request.getBrand());
        registerProduct.setProduct_image(request.getProduct_image());
        registerProduct.setInfo_image(request.getInfo_image());
        registerProduct.setBarcode(request.getBarcode());
        registerProduct.setEnable(0);
        registerProduct.setUser(user);
        return registerProduct;
    }

}
