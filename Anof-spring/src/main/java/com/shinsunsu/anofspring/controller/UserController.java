package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Ingredient;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    //사용자 아이디, 패스워드, 닉네임, 알러지, 성분 받아서 엔티티에 저장
    //return은 ResponseEntity로 상태코드까지 전달
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody Map<String,Object> newUser) {

        userService.join(newUser);
        System.out.println("join");
        return new ResponseEntity<Boolean>(true, HttpStatus.OK); //회원가입 완료 -> true
    }


    //유저 로그인 아이디 중복 체크
    @PostMapping("/checkUserId")
    public Boolean checkUserIdDuplicate(@RequestBody Map<String,String> paramMap) {
        String userId= (String) paramMap.get("userId");
        System.out.println(userId);
        return userService.checkLoginIdDuplicate(userId);
        //return true -> 존재하는 아이디 -> 회원가입 불가능
        //retrun fasle ->  사용 가능한 아이디
    }

    //유저 닉네임 중복 체크
    @PostMapping("/checkNickname")
    public Boolean checkNicknameDuplicate(@RequestBody Map<String,Object> paramMap) {
        String nickname= (String) paramMap.get("nickname");
        System.out.println(nickname);
        return userService.checkNicknameDuplicate(nickname);
        //return true -> 존재하는 닉네임 -> 회원가입 불가능
        //retrun fasle ->  사용 가능한 닉네임
    }

}