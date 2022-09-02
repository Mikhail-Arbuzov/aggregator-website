package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Compilation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation,Long> {
    @Query(value="SELECT * FROM compilations AS comp" +
            " ORDER BY comp.current_date_time DESC ",nativeQuery=true)
    List<Compilation> getAllCompilationsByLastCurrentTime();
}
