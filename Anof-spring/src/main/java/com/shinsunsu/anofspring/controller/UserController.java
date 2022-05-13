package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.service.UserService;
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

    UserService userService;

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

    //회원가입
    //사용자 아이디, 패스워드, 닉네임, 알러지, 성분 받아서 엔티티에 저장
    //return은
    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody Map<String,Object> paramMap) {
        System.out.println(paramMap.size());
        for(int i=0;i<paramMap.size();i++){
            //Map<String, String> res = (Map<String, String>) paramMap.get("username");       //json안에 리스트는 이걸로 받고

            //System.out.println(res.get("1"));

        }
        System.out.println(paramMap.get("userId"));   //그냥 키밸류는 이렇게 받고
        return null;
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