package com.shinsunsu.anofspring.service;

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

import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //회원가입
    @Transactional
    public User join(User newUser) {

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

    //유저아이디로 유저정보 조회
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return null;
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