package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.config.PasswordEncoderConfig;
import com.shinsunsu.anofspring.domain.Allergy;
import com.shinsunsu.anofspring.domain.Ingredient;
import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoderConfig passwordEncoderConfig;

    //회원가입
    @Transactional
    public User join(Map<String, Object> map) {

        System.out.println(map);

        String userId=(String) map.get("id");//이런식으로 하면됨
        String nickname = (String) map.get("nickname");
        String password = passwordEncoderConfig.passwordEncoder().encode((String)map.get("password")); //비밀번호 암호화

        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setPassword(password);
        newUser.setNickname(nickname);
        newUser.setRoles(Collections.singletonList("ROLE_USER")); //회원가입 시 role 유저로 설정

        Map<String, Integer> ingredient = (Map<String, Integer>) map.get("ingredient");

        Ingredient userIngredient = new Ingredient();
        userIngredient.setNatrium(ingredient.get("natrium"));
        userIngredient.setCarbohydrates(ingredient.get("carbohydrates"));
        userIngredient.setSugar(ingredient.get("sugar"));
        userIngredient.setFat(ingredient.get("fat"));
        userIngredient.setTrans_fat(ingredient.get("trans_fat"));
        userIngredient.setSaturated_fat(ingredient.get("saturated_fat"));
        userIngredient.setCholesterol(ingredient.get("cholesterol"));
        userIngredient.setProtein(ingredient.get("protein"));
        userIngredient.setCalorie(ingredient.get("calroie"));


        newUser.setIngredient(userIngredient); //성분 유저에 넣어줌

        Map<String, Integer> allergy = (Map<String, Integer>) map.get("allergy");

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
        userAllergy.setEgg((allergy.get("egg")));

        newUser.setAllergy(userAllergy); //알러지 유저에 넣어줌
        return userRepository.save(newUser);
    }


    //ID로 유저 정보 조회
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저의 아이디입니다."));
    }

    //토큰 업데이트
    public void updateToken(Long id, String token) {
        User user = findUserById(id);
        user.setAccessToken(token);
        userRepository.save(user);
    }

    //아이디로 유저 정보 조회
    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 아이디입니다"));
    }

    //유저 로그인 아이디 중복 체크
    @Transactional(readOnly = true)
    public boolean checkLoginIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
    }

    //유저 로그인 닉네임 중복 체크
    @Transactional(readOnly = true)
    public boolean checkNicknameDuplicate(String userId) {
        return userRepository.existsByNickname(userId);
    }
}