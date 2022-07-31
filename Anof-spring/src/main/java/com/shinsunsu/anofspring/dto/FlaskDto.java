package com.shinsunsu.anofspring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlaskDto {

    @Setter
    @Getter
    public static class request {
        private Long userId;
    }
}
