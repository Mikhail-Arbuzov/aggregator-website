package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends JpaRepository<Website,Long> {
     List<Website> findAllBySection(String section);

    @Query(value="SELECT * FROM websites AS w" +
            " WHERE w.section = :section ORDER BY  w.rating_in_country ASC, w.rating_in_world ASC ",nativeQuery=true)
    List<Website> getByWebsitesRating(@Param("section") String section);

    @Query(value="SELECT * FROM websites AS w" +
            " WHERE w.section = :section ORDER BY w.bounce_rate ASC ",nativeQuery=true)
    List<Website> getByWebsitesSection(@Param("section") String section);
}
