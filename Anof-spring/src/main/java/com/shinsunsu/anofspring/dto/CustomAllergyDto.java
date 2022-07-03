package com.shinsunsu.anofspring.dto;

import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomAllergyDto {
    int wheat;
    int milk;
    int buckwheat;
    int peanut;
    int soybean;
    int mackerel;
    int crab;
    int shrimp;
    int pork;
    int peach;
    int tomato;
    int walnut;
    int chicken;
    int beef;
    int squid;
    int shellfish;
    int egg;

    public CustomAllergyDto(Allergy allergy) {
        this.wheat = allergy.getWheat();
        this.milk = allergy.getMilk();
        this.buckwheat = allergy.getBuckwheat();
        this.peanut = allergy.getPeanut();
        this.soybean = allergy.getSoybean();
        this.mackerel = allergy.getMackerel();
        this.crab = allergy.getCrab();
        this.shrimp = allergy.getShrimp();
        this.pork = allergy.getPork();
        this.peach = allergy.getPeach();
        this.tomato = allergy.getTomato();
        this.walnut = allergy.getWalnut();
        this.chicken = allergy.getChicken();
        this.beef = allergy.getBeef();
        this.squid = allergy.getSquid();
        this.shellfish = allergy.getShellfish();
        this.egg = allergy.getEgg();
    }

    public CustomAllergyDto(Product product) {
        this.wheat = product.getWheat();
        this.milk = product.getMilk();
        this.buckwheat = product.getBuckwheat();
        this.peanut = product.getPeanut();
        this.soybean = product.getSoybean();
        this.mackerel = product.getMackerel();
        this.crab = product.getCrab();
        this.shrimp = product.getShrimp();
        this.pork = product.getPork();
        this.peach = product.getPeach();
        this.tomato = product.getTomato();
        this.walnut = product.getWalnut();
        this.chicken = product.getChicken();
        this.beef = product.getBeef();
        this.squid = product.getSquid();
        this.shellfish = product.getShellfish();
        this.egg = product.getEgg();
    }

    public List CustomAllergy() {
        List allergyList = new ArrayList<>();
        allergyList.add(wheat);
        allergyList.add(milk);
        allergyList.add(buckwheat);
        allergyList.add(peanut);
        allergyList.add(soybean);
        allergyList.add(mackerel);
        allergyList.add(crab);
        allergyList.add(shrimp);
        allergyList.add(pork);
        allergyList.add(peach);
        allergyList.add(tomato);
        allergyList.add(walnut);
        allergyList.add(chicken);
        allergyList.add(beef);
        allergyList.add(squid);
        allergyList.add(shellfish);
        allergyList.add(egg);
        return allergyList;
    }

}
