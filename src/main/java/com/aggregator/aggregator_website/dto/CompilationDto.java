package com.aggregator.aggregator_website.dto;

import com.aggregator.aggregator_website.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String avatarUser;
    private String firstName;
    private String secondName;
    private LocalDateTime currentTime;
    private String destiny;
    private List<Product> products;
}
