package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Device;
import com.aggregator.aggregator_website.entities.MonitoringPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {
    List<Device> findAllBySection(String section);
    List<Device> findAllByDestination(String destination);

    @Query(value="SELECT * FROM devices AS d" +
            " WHERE d.section = :section AND d.destination = :destination",nativeQuery=true)
    List<Device> getDevicesBySectionAndDestination(@Param("section") String section,@Param("destination")String destination);
}
