package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Configurator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguratorRepository extends JpaRepository<Configurator,Long> {

    @Query(value="SELECT * FROM configurators AS c" +
            " ORDER BY c.time_on_site DESC ",nativeQuery=true)
    List<Configurator> getAllConfiguratorsByLastTimeOnSite();
}
