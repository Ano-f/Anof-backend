package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomProductIngredientResponse {
    String natrium;
    String carbohydrates;
    String sugar;
    String fat;
    String trans_fat;
    String saturated_fat;
    String cholesterol;
    String protein;
    String calorie;

    public CustomProductIngredientResponse(Product product) {
        this.natrium = product.getNatrium();
        this.carbohydrates = product.getCarbohydrates();
        this.sugar = product.getSugar();
        this.fat = product.getFat();
        this.trans_fat = product.getTrans_fat();
        this.saturated_fat = product.getSaturated_fat();
        this.cholesterol = product.getCholesterol();
        this.protein = product.getProtein();
        this.calorie = product.getCalorie();
    }

    public List CustomIngredient() {
        List ingredientList = new ArrayList();
        ingredientList.add(natrium);
        ingredientList.add(carbohydrates);
        ingredientList.add(sugar);
        ingredientList.add(fat);
        ingredientList.add(trans_fat);
        ingredientList.add(saturated_fat);
        ingredientList.add(cholesterol);
        ingredientList.add(protein);
        ingredientList.add(calorie);

        return ingredientList;
    }
}
