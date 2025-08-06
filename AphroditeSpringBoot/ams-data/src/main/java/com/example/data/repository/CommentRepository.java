package com.example.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
