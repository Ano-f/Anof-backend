package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Allergy;
import lombok.Getter;


@Getter
public class AllergyRequest {

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

    public static Allergy newAllergy(AllergyRequest allergyRequest){
        Allergy allergy = new Allergy();
        allergy.setWheat(allergyRequest.getWheat());
;       allergy.setMilk(allergyRequest.getMilk());
        allergy.setBuckwheat(allergyRequest.getBuckwheat());
        allergy.setPeanut(allergyRequest.getPeanut());
        allergy.setSoybean(allergyRequest.getSoybean());
        allergy.setMackerel(allergyRequest.getMackerel());
        allergy.setCrab(allergyRequest.getCrab());
        allergy.setShrimp(allergyRequest.getShrimp());
        allergy.setPork(allergyRequest.getPork());
        allergy.setPeach(allergyRequest.getPeach());
        allergy.setTomato(allergyRequest.getTomato());
        allergy.setWalnut(allergyRequest.getWalnut());
        allergy.setChicken(allergyRequest.getChicken());
        allergy.setBeef(allergyRequest.getBeef());
        allergy.setSquid(allergyRequest.getSquid());
        allergy.setShellfish(allergyRequest.getShellfish());
        allergy.setEgg(allergyRequest.getEgg());

        return allergy;
    }



}
