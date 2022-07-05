package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.CustomAllergyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //유저 로그인 아이디 중복 체크
    boolean existsByUserId(String UserId);
    //유저 닉네임 중복 체크
    boolean existsByNickname(String nickname);
    Optional<User> findByUserId(String UserId);

    @Query("select new com.shinsunsu.anofspring.dto.CustomAllergyDto(u.allergy) from User u where u.userId = :userId")
    CustomAllergyDto findAllergy(@Param("userId") String userId);

}
