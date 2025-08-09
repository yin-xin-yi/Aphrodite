package com.example.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.data.entity.Post;

/**
 * @Repository它负责与数据源（比如数据库）进行交互
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllWithUserOrderByCreatedAtDesc();

    List<Post> findByUserId(Long userId);

    /**
     * 、
     * 这个 @Modifying 的作用就是 更新数据 删除数据 插入数据 告诉 spring Data JPA 这个就是一个 修改查询 这个 返回类型
     * 一般是 int 或者 void
     *
     * @Param 它放在方法参数前面，括号内是你在 @Query 字符串中定义的命名参数的名称（不带冒号）
     */
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    int incrementViewCount(@Param("postId") Long postId);
}
