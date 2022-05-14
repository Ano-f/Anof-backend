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
    @Autowired
    private PasswordEncoder encoder;


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
        String userId=(String) paramMap.get("userId");  //이런식으로 하면됨
        String nickname = (String) paramMap.get("nickname");
        String rawPassword = (String) paramMap.get("password");
        String password = encoder.encode(rawPassword); //비밀번호 암호화

        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setNickname(nickname);

        Map<String, Integer> ingredient = (Map<String, Integer>) paramMap.get("ingredient");
        int natrium = ingredient.get("natrium");
        int carbohydrates = ingredient.get("carbohydrates");
        int sugar = ingredient.get("sugar");
        int fat = ingredient.get("fat");
        int trans_fat = ingredient.get("trans_fat");
        int saturated_fat = ingredient.get("saturated_fat");
        int cholesterol = ingredient.get("cholesterol");
        int protein = ingredient.get("protein");

        Ingredient userIngredient = new Ingredient();
        userIngredient.setNatrium(natrium);
        userIngredient.setCarbohydrates(carbohydrates);
        userIngredient.setCarbohydrates(sugar);
        userIngredient.setCarbohydrates(fat);
        userIngredient.setCarbohydrates(trans_fat);
        userIngredient.setCarbohydrates(saturated_fat);
        userIngredient.setCarbohydrates(cholesterol);
        userIngredient.setCarbohydrates(protein);

        user.setIngredient(userIngredient); //성분 유저에 넣어줌

        Map<String, Integer> allergy = (Map<String, Integer>) paramMap.get("allergy");
        int wheat = allergy.get("wheat");
        int milk = allergy.get("milk");
        int buckwheat = allergy.get("buckwheat");
        int peanut = allergy.get("peanut");
        int soybean = allergy.get("soybean");
        int mackerel = allergy.get("mackerel");
        int crab = allergy.get("crab");
        int shrimp = allergy.get("shrimp");
        int pork = allergy.get("pork");
        int peach = allergy.get("peach");
        int tomato = allergy.get("tomato");
        int walnut = allergy.get("walnut");
        int chicken = allergy.get("chicken");
        int beef = allergy.get("beef");
        int squid = allergy.get("squid");
        int shellfish = allergy.get("shellfish");

        Allergy userAllergy = new Allergy();
        userAllergy.setWheat(wheat);
        userAllergy.setMilk(milk);
        userAllergy.setBuckwheat(buckwheat);
        userAllergy.setPeanut(peanut);
        userAllergy.setSoybean(soybean);
        userAllergy.setMackerel(mackerel);
        userAllergy.setCrab(crab);
        userAllergy.setShrimp(shrimp);
        userAllergy.setPork(pork);
        userAllergy.setPeach(peach);
        userAllergy.setTomato(tomato);
        userAllergy.setWalnut(walnut);
        userAllergy.setChicken(chicken);
        userAllergy.setBeef(beef);
        userAllergy.setSquid(squid);
        userAllergy.setShellfish(shellfish);

        user.setAllergy(userAllergy); //알러지 유저에 넣어줌

        userService.join(user);
        //Map<String, String> res = (Map<String, String>) paramMap.get("username");       //json안에 리스트는 이걸로 받고
        //System.out.println(res.get("1"));

        System.out.println(paramMap.get("userId"));   //그냥 키밸류는 이렇게 받고
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