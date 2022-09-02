package com.aggregator.aggregator_website.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compilation_seq")
    @SequenceGenerator(name ="compilation_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "avatar_user")
    private String avatarUser;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "current_date_time",columnDefinition = "TIMESTAMP")
    private LocalDateTime currentTime;

    @Column(name = "destiny")
    private String destiny;

    @OneToMany(mappedBy = "compilation",cascade = CascadeType.ALL)
    private List<Product> products;
}
