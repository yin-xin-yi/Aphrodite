package com.example.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.data.entity.PostLike;
import com.example.data.entity.PostLikeId;

import jakarta.transaction.Transactional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    Optional<PostLike> findByUser_IdAndPost_Id(Long userId, Long postId);

    boolean existsByUser_IdAndPost_Id(Long userId, Long postId);

    @Modifying
    @Transactional
    void deleteByPost_Id(Long postId);

    @Modifying
    @Transactional
    void deleteByUser_Id(Long userId);
}
