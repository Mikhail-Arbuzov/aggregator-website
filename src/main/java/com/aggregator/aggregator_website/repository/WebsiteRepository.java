package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends JpaRepository<Website,Long> {
    List<Website> findBySection(String section);

    @Query(value="SELECT w.icon_site,w.time_on_site,w.visits,w.bounce_rate FROM websites AS w" +
            " WHERE w.section = :section ORDER BY w.bounce_rate ASC ",nativeQuery=true)
    List<Website> getByWebsitesSection(@Param("section") String section);
}
