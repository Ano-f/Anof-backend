package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.request.updateUserRequest;
import com.shinsunsu.anofspring.dto.response.PointDetailResponse;
import com.shinsunsu.anofspring.dto.response.UserResponse;
import com.shinsunsu.anofspring.service.MypageService;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MypageController {

    @Autowired private UserService userService;
    @Autowired private MypageService mypageService;

    //위험 성분 분석
    @GetMapping("/danger")
    public ResponseEntity<Object> getDangerIngredient(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());

        Map<String, Integer> md = mypageService.getDangerIngredient(user); //공통 알러지 카운트 한 것(1도 포함됨)
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //포인트 적립 내역
    @GetMapping("/pointdetail")
    public ResponseEntity<List<PointDetailResponse>> getPointDetail(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(mypageService.getPointDetail(user), HttpStatus.OK);
    }

    //정보 수정 전 사용자 정보 확인
    @GetMapping("/checkUserInfo")
    public ResponseEntity<UserResponse.userInfoResponse> getUserInfo(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(mypageService.getUserInfo(user), HttpStatus.OK);
    }

    //사용자 정보 수정
    @PatchMapping("/updateUser")
    public ResponseEntity<Boolean> updateUserInfo(@RequestBody updateUserRequest updateInfo, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(mypageService.updateUserInfo(updateInfo, user), HttpStatus.OK);
    }
}
