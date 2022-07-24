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
@Table(name="details")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "detail_seq")
    @SequenceGenerator(name ="detail_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "secondname")
    private String secondName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "avatarka")
    private String avatarka;

    @Column(name = "description")
    private String description;

    @Column(name = "vk_network")
    private String vkNetwork;

    @Column(name = "classmates_network")
    private String classmatesNetwork;

    @Column(name = "telegram_network")
    private String telegramNetwork;
}
