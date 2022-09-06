package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareRepository extends JpaRepository<Software,Long> {
}
