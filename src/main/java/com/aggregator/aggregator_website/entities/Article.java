package com.aggregator.aggregator_website.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "article_seq")
    @SequenceGenerator(name ="article_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "image",columnDefinition = "text")
    private String image;

    @Column(name = "title")
    private String title;

    @Column(name = "current_date_time",columnDefinition = "TIMESTAMP")
    private LocalDateTime currentDateTime;

    @Column(name = "link")
    private String link;
}
