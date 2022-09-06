package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.config.MapperConfig;
import com.aggregator.aggregator_website.dto.ArticleDto;
import com.aggregator.aggregator_website.dto.PageDto;
import com.aggregator.aggregator_website.entities.Article;
import com.aggregator.aggregator_website.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ParsingPage parsingPage;
    private final ModelMapper modelMapper;

    public List<ArticleDto> getAllArticles(){
        List<Article> articles = articleRepository.getAllArticlesByLastCurrentDateTime();
        if(articles.size() > 0){
            return MapperConfig.convertList(articles,this::mapToDto);
        }
        else {
            List<ArticleDto> articleDtoList = new ArrayList<>();
            ArticleDto article2 = getDefaultArticle();
            articleDtoList.add(article2);
            return articleDtoList;
        }
    }

    @Transactional
    public void addArticle(String url) throws IOException,NullPointerException {
        ArticleDto articleDto = new ArticleDto();
        PageDto page = parsingPage.getParsePage(url);
        articleDto.setImage(page.getImageUrl());
        articleDto.setTitle(page.getTitle());
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentTime = localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime currentDateTime = LocalDateTime.parse(currentTime,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        articleDto.setCurrentDateTime(currentDateTime);
        articleDto.setLink(page.getLink());

        Article article = modelMapper.map(articleDto,Article.class);
        articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(Long id){
        Article article1 = articleRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        articleRepository.delete(article1);
    }

    private ArticleDto getDefaultArticle(){
        ArticleDto dto = new ArticleDto();
        dto.setId(Long.valueOf(0));
        dto.setImage("img/comp.jpeg");
        dto.setTitle("Как сделать красивую подсветку корпуса компьютера?");
        String time = "25.05.2022 09:45";
        LocalDateTime dateTime = LocalDateTime.parse(time,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        dto.setCurrentDateTime(dateTime);
        dto.setLink("https://simplelight.info/svetodizayn/podsvetka-kompyutera.html");
        return dto;
    }

    private ArticleDto mapToDto(Article article){
        ArticleDto articleDto2 = modelMapper.map(article,ArticleDto.class);
        return articleDto2;
    }
}
