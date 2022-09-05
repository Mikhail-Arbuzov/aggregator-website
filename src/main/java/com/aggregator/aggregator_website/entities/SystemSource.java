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
@Table(name="system_sources")
public class SystemSource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "source_seq")
    @SequenceGenerator(name ="source_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "logotype")
    private String logotype;

    @Column(name = "domain")
    private String domain;

    @Column(name = "image",columnDefinition = "text")
    private String image;

    @Column(name = "title")
    private String title;

    @Column(name = "text",columnDefinition = "text")
    private String text;

    @Column(name = "link",columnDefinition = "text")
    private String link;
}
