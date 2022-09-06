package com.aggregator.aggregator_website.controllers;

import com.aggregator.aggregator_website.dto.ArticleDto;
import com.aggregator.aggregator_website.services.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/allForPC/articles")
    public String getArticlePage(Model model){
        List<ArticleDto> articles = articleService.getAllArticles();
        model.addAttribute("articles",articles);
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        model.addAttribute("dateTime",dateTime);
        return "article";
    }

    @DeleteMapping("/allForPC/articles/id")
    public String deleteArticle(@RequestParam("id") Long id){
        articleService.deleteArticle(id);
        return "redirect:/allForPC/articles";
    }
}
