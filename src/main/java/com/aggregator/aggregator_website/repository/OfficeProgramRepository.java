package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.OfficeProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeProgramRepository extends JpaRepository<OfficeProgram,Long> {
}
