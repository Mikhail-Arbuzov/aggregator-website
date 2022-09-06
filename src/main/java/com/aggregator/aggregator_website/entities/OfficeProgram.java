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
@Table(name="office_programs")
public class OfficeProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "office_seq")
    @SequenceGenerator(name ="office_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url",columnDefinition = "text")
    private String imageUrl;

    @Column(name = "header")
    private String header;

    @Column(name = "text",columnDefinition = "text")
    private String text;

    @Column(name = "link",columnDefinition = "text")
    private String link;
}
