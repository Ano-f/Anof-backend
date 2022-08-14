package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.PointDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointDetailResponse {

    Long id;
    String productname;
    int point;
    String date;

    public static PointDetailResponse pointDetailResponse(PointDetail pointDetail) {
        return PointDetailResponse.builder()
                .id(pointDetail.getId())
                .productname(pointDetail.getProduct().getName())
                .point(pointDetail.getPoint())
                .date(pointDetail.getDate())
                .build();
    }
}
