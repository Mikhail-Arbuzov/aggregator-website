package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<Detail,Long> {
    Optional<Detail> findByEmail(String email);
}
