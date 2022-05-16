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
    private final PasswordEncoder encoder;


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
    //return은 ResponseEntity로 상태코드까지 전달
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody Map<String,Object> paramMap) {
        String userId=(String) paramMap.get("id");//이런식으로 하면됨
        String nickname = (String) paramMap.get("nickname");
        String password = encoder.encode((String)paramMap.get("password")); //비밀번호 암호화

        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setRoles(Collections.singletonList("ROLE_USER")); //회원가입 시 role 유저로 설정

        Map<String, Integer> ingredient = (Map<String, Integer>) paramMap.get("ingredient");

        Ingredient userIngredient = new Ingredient();
        userIngredient.setNatrium(ingredient.get("natrium"));
        userIngredient.setCarbohydrates(ingredient.get("carbohydrates"));
        userIngredient.setCarbohydrates(ingredient.get("sugar"));
        userIngredient.setCarbohydrates(ingredient.get("fat"));
        userIngredient.setCarbohydrates(ingredient.get("trans_fat"));
        userIngredient.setCarbohydrates(ingredient.get("saturated_fat"));
        userIngredient.setCarbohydrates(ingredient.get("cholesterol"));
        userIngredient.setCarbohydrates(ingredient.get("protein"));

        user.setIngredient(userIngredient); //성분 유저에 넣어줌

        Map<String, Integer> allergy = (Map<String, Integer>) paramMap.get("allergy");

        Allergy userAllergy = new Allergy();
        userAllergy.setWheat(allergy.get("wheat"));
        userAllergy.setMilk(allergy.get("milk"));
        userAllergy.setBuckwheat(allergy.get("buckwheat"));
        userAllergy.setPeanut(allergy.get("peanut"));
        userAllergy.setSoybean(allergy.get("soybean"));
        userAllergy.setMackerel(allergy.get("mackerel"));
        userAllergy.setCrab(allergy.get("crab"));
        userAllergy.setShrimp(allergy.get("shrimp"));
        userAllergy.setPork(allergy.get("pork"));
        userAllergy.setPeach(allergy.get("peach"));
        userAllergy.setTomato(allergy.get("tomato"));
        userAllergy.setWalnut(allergy.get("walnut"));
        userAllergy.setChicken(allergy.get("chicken"));
        userAllergy.setBeef(allergy.get("beef"));
        userAllergy.setSquid(allergy.get("squid"));
        userAllergy.setShellfish(allergy.get("shellfish"));

        user.setAllergy(userAllergy); //알러지 유저에 넣어줌

        userService.join(user);
        //Map<String, String> res = (Map<String, String>) paramMap.get("username");       //json안에 리스트는 이걸로 받고
        //System.out.println(res.get("1"));

        System.out.println("login");   //그냥 키밸류는 이렇게 받고
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