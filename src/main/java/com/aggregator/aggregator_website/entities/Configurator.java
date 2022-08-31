package com.aggregator.aggregator_website.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="configurators")
public class Configurator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "configurator_seq")
    @SequenceGenerator(name ="configurator_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "logotype",columnDefinition = "text")
    private String logotype;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "visits")
    private BigDecimal visits;

    @Column(name = "bounce_rate")
    private double bounceRate;

    @Column(name = "time_on_site")
    private LocalTime timeOnSite;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @Column(name = "link",columnDefinition = "text")
    private String link;
}
