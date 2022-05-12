package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    //회원가입
    /*
    @PostMapping("/join")
    public @ResponseBody JSONArray join(HttpServletRequest request) {
        System.out.println("접속");
        User newUser =new User();
        newUser.setUserId(request.getParameter("userId"));
        newUser.setPassword(request.getParameter("password"));
        System.out.println(request.getParameter("userId"));
        System.out.println(request.getParameter("password"));
        return null;
    }

     */

    @PostMapping("/join")
    public @ResponseBody JSONArray join(HttpServletRequest request) {
        System.out.println("접속");
        User newUser =new User();
        newUser.setUserId(request.getParameter("userId"));
        newUser.setPassword(request.getParameter("password"));
        System.out.println(request.getParameter("userId"));
        System.out.println(request.getParameter("password"));
        return null;
    }

    //test
    @PostMapping("/hello")
    public @ResponseBody JSONArray hello() {
        System.out.println("접속");

        return null;
    }


    //유저 로그인 아이디 중복 체크
    @GetMapping("/checkUserId/{userId}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        System.out.println("접속!!");
        System.out.println(userId);
        return null;
    }

}