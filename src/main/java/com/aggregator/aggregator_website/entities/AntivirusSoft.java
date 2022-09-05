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
@Table(name="antivirus_softs")
public class AntivirusSoft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "antivirus_seq")
    @SequenceGenerator(name ="antivirus_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "img_url",columnDefinition = "text")
    private String imgURL;

    @Column(name = "title")
    private String title;

    @Column(name = "link",columnDefinition = "text")
    private String link;
}
