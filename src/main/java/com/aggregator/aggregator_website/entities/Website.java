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
@Table(name="websites")
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "website_seq")
    @SequenceGenerator(name ="website_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "icon_site")
    private String iconSite;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "visits")
    private String visits;

    @Column(name = "time_on_site")
    private String timeOnSite;

    @Column(name = "bounce_rate")
    private double bounceRate;

    @Column(name = "rating_in_world")
    private int ratingInWorld;

    @Column(name = "rating_in_country")
    private int ratingInCountry;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "page_id")
    private Page page;

    @Column(name = "section")
    private String section;

}
