package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //유저 로그인 아이디 중복 체크
    boolean existsByUserId(String UserId);

    //유저 닉네임 중복 체크
    boolean existsByNickname(String nickname);

    //Optional<User> findByUserId(String UserId);
    Optional<User> findByUserId(String UserId);
}
