package com.aggregator.aggregator_website.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "device_seq")
    @SequenceGenerator(name ="device_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image", columnDefinition = "text")
    private String image;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "destination")
    private String destination;

    @Column(name = "section")
    private String section;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "device_id")
    @OneToMany(mappedBy = "device",cascade = CascadeType.ALL)
    private List<MonitoringPrice> monitoringPrices;
}
