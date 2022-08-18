package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Ingredient;
import com.shinsunsu.anofspring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserResponse {

    @Getter
    @AllArgsConstructor
    public static class userInfoResponse {

        private Allergy allergy;
        private Ingredient ingredient;

        public userInfoResponse(User user) {
            this.allergy = user.getAllergy();
            this.ingredient = user.getIngredient();

        }
    }
}
