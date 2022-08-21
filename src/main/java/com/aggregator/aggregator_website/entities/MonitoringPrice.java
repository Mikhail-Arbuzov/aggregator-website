package com.aggregator.aggregator_website.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="monitoring_prices")
public class MonitoringPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "monitoring_seq")
    @SequenceGenerator(name ="monitoring_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "logo_site",columnDefinition = "text")
    private String logoSite;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "price")
    private String price;

    @Column(name="link",columnDefinition = "text")
    private String link;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;
}
