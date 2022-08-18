package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Ingredient;
import lombok.Getter;

@Getter
public class updateUserRequest {

    Allergy allergy;
    Ingredient ingredient;

    public static void updateAllergy(Allergy allergy, Allergy newAllergy) {
        allergy.setWheat(newAllergy.getWheat());
        allergy.setMilk(newAllergy.getMilk());
        allergy.setBuckwheat(newAllergy.getBuckwheat());
        allergy.setPeanut(newAllergy.getPeanut());
        allergy.setSoybean(newAllergy.getSoybean());
        allergy.setMackerel(newAllergy.getMackerel());
        allergy.setCrab(newAllergy.getCrab());
        allergy.setShrimp(newAllergy.getShrimp());
        allergy.setPork(newAllergy.getPork());
        allergy.setPeach(newAllergy.getPeach());
        allergy.setTomato(newAllergy.getTomato());
        allergy.setWalnut(newAllergy.getWalnut());
        allergy.setChicken(newAllergy.getChicken());
        allergy.setBeef(newAllergy.getBeef());
        allergy.setSquid(newAllergy.getSquid());
        allergy.setShellfish(newAllergy.getShellfish());
        allergy.setEgg(newAllergy.getEgg());
    }

    public static void updateIngredient(Ingredient ingredient, Ingredient newIngredient) {
        ingredient.setNatrium(newIngredient.getNatrium());
        ingredient.setCarbohydrates(newIngredient.getCarbohydrates());
        ingredient.setSugar(newIngredient.getSugar());
        ingredient.setFat(newIngredient.getFat());
        ingredient.setTrans_fat(newIngredient.getTrans_fat());
        ingredient.setSaturated_fat(newIngredient.getSaturated_fat());
        ingredient.setCholesterol(newIngredient.getCholesterol());
        ingredient.setProtein(newIngredient.getProtein());
        ingredient.setCalorie(newIngredient.getCalorie());
    }

}
