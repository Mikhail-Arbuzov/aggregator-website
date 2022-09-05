package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.SystemSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSourceRepository extends JpaRepository<SystemSource,Long> {
}
