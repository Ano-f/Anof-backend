package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.User;
import com.shinsunsu.anofspring.dto.response.CustomAllergyResponse;
import com.shinsunsu.anofspring.dto.response.CustomUserIngredientResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //유저 로그인 아이디 중복 체크
    boolean existsByUserId(String UserId);
    //유저 닉네임 중복 체크
    boolean existsByNickname(String nickname);
    Optional<User> findByUserId(String UserId);

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomAllergyResponse(u.allergy) from User u where u.userId = :userId")
    CustomAllergyResponse findAllergy(@Param("userId") String userId);

    @Query("select new com.shinsunsu.anofspring.dto.response.CustomUserIngredientResponse(u.ingredient) from User u where u.userId = :userId")
    CustomUserIngredientResponse findIngredient(@Param("userId") String userId);

    @Query("select u.id from User u where u.userId = :userId")
    Long findIdByUserId(@Param("userId") String userId);

    List<User> findTopByOrderByRankingDesc();
    int countByRanking(Long ranking);
    List<User> findTop50ByOrderByRanking();
    List<User> findByRanking(Long Ranking);

    @Query("select u from User u where (select max(u.ranking) from User u where u.ranking < :ranking) = u.ranking")
    List<User> findByHighRanking(@Param("ranking") Long ranking);
}
