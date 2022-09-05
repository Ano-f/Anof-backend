package com.shinsunsu.anofspring.service;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.exception.user.UserNotFoundException;
import com.shinsunsu.anofspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;

    //회원가입
    @Transactional
    public User join(User newUser){

        if (newUser.getRoles() != Collections.singletonList("ROLE_ADMIN")) {
            redisTemplate.opsForZSet().add("ranking", newUser.getNickname(), newUser.getPoint());
        }

        return userRepository.save(newUser);
    }

    //ID로 유저 정보 조회
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저의 아이디입니다."));
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
                .orElseThrow(() -> new UsernameNotFoundException("회원가입이 필요합니다."));
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