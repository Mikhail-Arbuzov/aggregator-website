package com.aggregator.aggregator_website.repository;

import com.aggregator.aggregator_website.entities.Detail;
import com.aggregator.aggregator_website.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByDetail(Detail detail);
}
