package com.shinsunsu.anofspring.dto.request;


import com.shinsunsu.anofspring.domain.Ingredient;
import lombok.Getter;

@Getter
public class IngredientRequest {

    int natrium;
    int carbohydrates;
    int sugar;
    int fat;
    int trans_fat;
    int saturated_fat;
    int cholesterol;
    int protein;
    int calorie;

    public static Ingredient newIngredient(IngredientRequest ingredientRequest){
        Ingredient ingredient = new Ingredient();
        ingredient.setNatrium(ingredientRequest.getNatrium());
        ingredient.setCarbohydrates(ingredientRequest.getCarbohydrates());
        ingredient.setSugar(ingredient.getSugar());
        ingredient.setFat(ingredientRequest.getFat());
        ingredient.setTrans_fat(ingredientRequest.getTrans_fat());
        ingredient.setSaturated_fat(ingredientRequest.getSaturated_fat());
        ingredient.setCholesterol(ingredientRequest.getCholesterol());
        ingredient.setProtein(ingredientRequest.getProtein());
        ingredient.setCalorie(ingredientRequest.getCalorie());

        return ingredient;
    }
}
