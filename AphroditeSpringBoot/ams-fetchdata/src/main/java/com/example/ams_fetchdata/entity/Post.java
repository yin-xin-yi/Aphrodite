package com.example.ams_fetchdata.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

//  @Data Lombok 库 就是有很多 get set 等方式很方便
//  @Entity 就是一个实体 
@Data
@Entity
@Table(name = "posts")
public class Post {

    /**
     * GenerationType.IDENTITY 它表示主键的生成工作完全委托给数据库的自增列功能
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @ManyToOne JPA 风格 表示多对1 查询一个 Post 对象时 不要立刻去数据库里把关联的 User 对象也查出来
     * 只有当我的代码第一次调用 post.getUser() 再去找这个User
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "like_count")
    private int likeCount = 0;

    @Column(name = "comment_count")
    private int commentCount = 0;

    @Column(name = "bookmark_count")
    private int bookmarkCount = 0;

    @Column(name = "is_anonymous")
    private boolean isAnonymous = false;

    private String status;

    /**
     * updatable = false 它告诉 JPA：“这个列的值在插入后就不能再被更新了”
     */
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp // 让Hibernate在插入时自动填充创建时间
    private OffsetDateTime createdAt;

    /**
     * 当这个实体被更新并保存时，自动将当前的时间戳填入这个字段
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
