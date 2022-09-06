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
@Table(name="softwares")
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "soft_seq")
    @SequenceGenerator(name ="soft_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "icon")
    private String icon;

    @Column(name = "site")
    private String site;

    @Column(name = "img",columnDefinition = "text")
    private String img;

    @Column(name = "title")
    private String title;

    @Column(name = "text",columnDefinition = "text")
    private String text;

    @Column(name = "link",columnDefinition = "text")
    private String link;
}
