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

    @Getter
    @AllArgsConstructor
    public static class rankingResponse {

        private String nickname;
        private Long ranking;
        private int point;

        public rankingResponse(User user) {
            this.nickname = user.getNickname();
            this.ranking = user.getRanking();
            this.point = user.getPoint();
        }
/*
        public rankingResponse(Long rank, String nickname, int point) {
            this.ranking = rank;
            this.nickname = nickname;
            this.point = point;
        }

 */
    }
}
