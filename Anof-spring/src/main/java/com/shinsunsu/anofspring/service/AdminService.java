package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.RegisterProduct;
import com.shinsunsu.anofspring.dto.response.RegisterRequestProductResponse;
import com.shinsunsu.anofspring.repository.RegisterProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final RegisterProductRepository registerProductRepository;

    public List<RegisterRequestProductResponse> getRegisterRequestProductList() {
        List<RegisterProduct> registerProducts = registerProductRepository.findByEnable(1);
        List<RegisterRequestProductResponse> registList = new ArrayList<>();

        for(RegisterProduct registerProduct : registerProducts) {
            registList.add(new RegisterRequestProductResponse(registerProduct));
        }

        return registList;

    }
}
