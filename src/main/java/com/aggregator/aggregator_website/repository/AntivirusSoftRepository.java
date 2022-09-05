package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.AntivirusSoft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntivirusSoftRepository extends JpaRepository<AntivirusSoft,Long> {
}
