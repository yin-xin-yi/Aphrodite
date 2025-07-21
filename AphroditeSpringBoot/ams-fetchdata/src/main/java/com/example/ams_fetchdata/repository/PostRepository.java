package com.example.ams_fetchdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ams_fetchdata.entity.Post;

/**
 * @Repository它负责与数据源（比如数据库）进行交互
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByOrderByCreatedAtDesc();
}
