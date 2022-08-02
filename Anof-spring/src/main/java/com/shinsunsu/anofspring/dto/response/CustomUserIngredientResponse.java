package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Ingredient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomUserIngredientResponse {

    int natrium;
    int carbohydrates;
    int sugar;
    int fat;
    int trans_fat;
    int saturated_fat;
    int cholesterol;
    int protein;
    int calorie;

    public CustomUserIngredientResponse(Ingredient ingredient) {
        this.natrium = ingredient.getNatrium();
        this.carbohydrates = ingredient.getCarbohydrates();
        this.sugar = ingredient.getSugar();
        this.fat = ingredient.getFat();
        this.trans_fat = ingredient.getTrans_fat();
        this.saturated_fat = ingredient.getSaturated_fat();
        this.cholesterol = ingredient.getCholesterol();
        this.protein = ingredient.getProtein();
        this.calorie = ingredient.getCalorie();
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
