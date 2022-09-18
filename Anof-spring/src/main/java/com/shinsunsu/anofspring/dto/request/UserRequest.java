package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Ingredient;
import com.shinsunsu.anofspring.domain.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Collections;

@Getter
public class UserRequest {

    @NotBlank String userId;
    @NotBlank String password;
    @NotBlank String nickname;
    @NotBlank Allergy allergy;
    @NotBlank Ingredient ingredient;
    String roles;

    public static User newUser(UserRequest userRequest, String encodePW, Allergy allergy, Ingredient ingredient) {
        User user = new User();
        user.setAllergy(allergy);
        user.setIngredient(ingredient);
        user.setUserId(userRequest.getUserId());
        user.setPassword(encodePW);
        user.setNickname(userRequest.getNickname());
        user.setRoles(Collections.singletonList("ROLE_USER"));

        return user;
    }

    public static User newAdmin(UserRequest userRequest, String encodePW, Allergy allergy, Ingredient ingredient) {
        User user = new User();
        user.setAllergy(allergy);
        user.setIngredient(ingredient);
        user.setUserId(userRequest.getUserId());
        user.setPassword(encodePW);
        user.setNickname(userRequest.getNickname());
        user.setRoles(Collections.singletonList("ROLE_ADMIN"));

        return user;
    }

}
