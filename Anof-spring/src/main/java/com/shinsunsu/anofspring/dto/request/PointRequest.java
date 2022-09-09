package com.shinsunsu.anofspring.dto.request;

import com.shinsunsu.anofspring.domain.PointDetail;
import com.shinsunsu.anofspring.domain.Product;
import com.shinsunsu.anofspring.domain.User;
import lombok.Getter;

@Getter
public class PointRequest {

    User user;
    Product product;
    int point;

    public static PointDetail PointDetailRequest(User user, Product product, int point) {
        PointDetail pointDetail = new PointDetail();
        pointDetail.setUser(user);
        pointDetail.setProduct(product);
        pointDetail.setPoint(point);
        return pointDetail;
    }
}
