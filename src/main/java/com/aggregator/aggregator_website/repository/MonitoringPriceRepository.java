package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.entities.MonitoringPrice;
import com.aggregator.aggregator_website.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitoringPriceRepository extends JpaRepository<MonitoringPrice,Long> {
    //Optional<MonitoringPrice> findBySiteName(String siteName);
    Optional<MonitoringPrice> findBySiteNameAndDevice(String siteName, Device device);
}
