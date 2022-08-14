package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.PointDetail;
import com.shinsunsu.anofspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {

    List<PointDetail> findByUser(User user);

}
