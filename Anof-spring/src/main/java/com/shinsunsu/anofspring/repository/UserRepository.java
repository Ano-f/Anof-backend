package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
