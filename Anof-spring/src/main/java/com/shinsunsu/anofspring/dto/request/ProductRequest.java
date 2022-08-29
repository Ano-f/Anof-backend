package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.RegisterProduct;
import com.shinsunsu.anofspring.domain.User;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;


public class ProductRequest {

    @Getter
    public static class registerProductRequest {

        private String name;
        private String brand;
        private String product_image;
        private String info_image;
        private String barcode;
        private int enable;
        private User user;

        public static RegisterProduct RegisterProduct(registerProductRequest request, User user) {
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

    @Getter
    public static class addProductRequest {

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
        private int wheat;
        private int milk;
        private int buckwheat;
        private int peanut;
        private int soybean;
        private int mackerel;
        private int crab;
        private int shrimp;
        private int pork;
        private int peach;
        private int tomato;
        private int walnut;
        private int chicken;
        private int beef;
        private int squid;
        private int shellfish;
        private int egg;
        private String image;
        private String barcode;

        private Long userId;

        public static Product addProduct(addProductRequest request) {
            Product newProduct = new Product();
            newProduct.setName(request.getName());
            newProduct.setType(request.getType());
            newProduct.setBrand(request.getBrand());
            newProduct.setRawMaterial(request.getRawMaterial());
            newProduct.setNatrium(request.getNatrium());
            newProduct.setCarbohydrates(request.getCarbohydrates());
            newProduct.setSugar(request.getSugar());
            newProduct.setFat(request.getFat());
            newProduct.setTrans_fat(request.getTrans_fat());
            newProduct.setSaturated_fat(request.getSaturated_fat());
            newProduct.setCholesterol(request.getCholesterol());
            newProduct.setProtein(request.getProtein());
            newProduct.setCalorie(request.getCalorie());
            newProduct.setCapacity(request.getCapacity());
            newProduct.setWheat(request.getWheat());
            newProduct.setMilk(request.getMilk());
            newProduct.setBuckwheat(request.getBuckwheat());
            newProduct.setPeanut(request.getPeanut());
            newProduct.setSoybean(request.getSoybean());
            newProduct.setMackerel(request.getMackerel());
            newProduct.setCrab(request.getCrab());
            newProduct.setShrimp(request.getShrimp());
            newProduct.setPork(request.getPork());
            newProduct.setPeach(request.getPeach());
            newProduct.setTomato(request.getTomato());
            newProduct.setWalnut(request.getWalnut());
            newProduct.setChicken(request.getChicken());
            newProduct.setBeef(request.getBeef());
            newProduct.setSquid(request.getSquid());
            newProduct.setShellfish(request.getShellfish());
            newProduct.setEgg(request.getEgg());
            newProduct.setImage(request.getImage());
            newProduct.setBarcode(request.getBarcode());

            return newProduct;
        }
    }

}
