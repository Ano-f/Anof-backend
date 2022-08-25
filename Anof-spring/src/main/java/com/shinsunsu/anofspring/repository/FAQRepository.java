package com.shinsunsu.anofspring.repository;

import com.shinsunsu.anofspring.domain.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
