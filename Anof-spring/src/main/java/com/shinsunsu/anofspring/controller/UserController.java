package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.config.JwtTokenProvider;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.exception.user.PasswordErrorException;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    //사용자 아이디, 패스워드, 닉네임, 알러지, 성분 받아서 엔티티에 저장
    //return은 ResponseEntity로 상태코드까지 전달
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody Map<String,Object> paramMap) {
        String userId=(String) paramMap.get("userId");  //이런식으로 하면됨
        //Map<String, String> res = (Map<String, String>) paramMap.get("username");       //json안에 리스트는 이걸로 받고
        //System.out.println(res.get("1"));

        System.out.println(paramMap.get("userId"));   //그냥 키밸류는 이렇게 받고
        return null;
    }


    //유저 로그인 아이디 중복 체크
    @PostMapping("/checkUserId")
    public Boolean checkUserIdDuplicate(@RequestBody Map<String,String> paramMap) {
        //String userId= (String) paramMap.get("!!!!");
        System.out.println("11111111");
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

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> user) {
        System.out.println(user.get("userId"));
        User loginUser = userService.loadUserByUsername(user.get("userId"));
        if (!passwordEncoder.matches(user.get("password"), loginUser.getPassword())) {
            throw new PasswordErrorException("잘못된 비밀번호입니다.");
        }

        //토큰 생성 및 저장
        String token = jwtTokenProvider.createToken(loginUser.getUsername(), loginUser.getRoles()); //getUsername -> 유저 아이디 반환
        userService.updateToken(loginUser.getId(), token);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("nickname",loginUser.getNickname    ());
        map.put("userId",loginUser.getUserId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}