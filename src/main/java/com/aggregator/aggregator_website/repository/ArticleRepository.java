package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query(value="SELECT * FROM articles AS ar" +
    " ORDER BY ar.current_date_time DESC ", nativeQuery=true)
    List<Article> getAllArticlesByLastCurrentDateTime();
}
