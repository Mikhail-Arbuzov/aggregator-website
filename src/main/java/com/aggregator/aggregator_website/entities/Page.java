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
@Table(name="pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "page_seq")
    @SequenceGenerator(name ="page_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url",columnDefinition = "text")
    private String imageUrl;

    @Column(name ="text",columnDefinition = "text")
    private String text;

    @Column(name="link",columnDefinition = "text")
    private String link;
}
