package com.shinsunsu.anofspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shinsunsu.anofspring.config.JwtTokenProvider;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.request.UserRequest;
import com.shinsunsu.anofspring.exception.user.PasswordErrorException;
import com.shinsunsu.anofspring.service.ArticleService;
import com.shinsunsu.anofspring.service.MainpageService;
import com.shinsunsu.anofspring.service.ProductService;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MainpageService mainpageService;
    private final ArticleService articleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody UserRequest user) {
        if(user.getUserId().equals("admin") & user.getPassword().equals("admin")) {
            User newAdmin = userService.join(UserRequest.newAdmin(user, passwordEncoder.encode(user.getPassword()), user.getAllergy(), user.getIngredient()));
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        }
        User newUser = userService.join(UserRequest.newUser(user, passwordEncoder.encode(user.getPassword()),user.getAllergy(), user.getIngredient()));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    //유저 로그인 아이디 중복 체크
    @GetMapping("/checkUserId/{userId}")
    public Boolean checkUserIdDuplicate(@PathVariable String userId) {
        return userService.checkLoginIdDuplicate(userId);
        //return true -> 존재하는 아이디 -> 회원가입 불가능
        //retrun fasle ->  사용 가능한 아이디
    }

    //유저 닉네임 중복 체크
    @GetMapping("/checkNickname/{nickname}")
    public Boolean checkNicknameDuplicate(@PathVariable String nickname) {
        return userService.checkNicknameDuplicate(nickname);
        //return true -> 존재하는 닉네임 -> 회원가입 불가능
        //retrun fasle ->  사용 가능한 닉네임
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequest user) throws JsonProcessingException {
        //https://velog.io/@kyu9610/Spring-Boot-%EC%87%BC%ED%95%91%EB%AA%B0-User-%EA%B4%80%EB%A6%AC%EC%9E%90-%EA%B8%B0%EB%8A%A5

        User loginUser = userService.loadUserByUsername(user.getUserId());

        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            throw new PasswordErrorException("잘못된 비밀번호입니다.");
        }

        //토큰 생성 및 저장
        String token = jwtTokenProvider.createToken(loginUser.getUsername(), loginUser.getRoles()); //getUsername -> 유저 아이디 반환
        userService.updateToken(loginUser.getId(), token);

        Map<String, Object> map = new HashMap<>();

        map.put("token", token);
        map.put("nickname",loginUser.getNickname());
        map.put("userId",loginUser.getUserId());

        if(user.getUserId().equals("admin") & user.getPassword().equals("admin")) {
            map.put("status", "관리자");
        }

        else {
            map.put("recommend", productService.recommend(loginUser.getUserId()));
            map.put("article", articleService.customArticle(loginUser.getUserId())); //맞춤 기사
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}