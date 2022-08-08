package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page,Long> {
}
