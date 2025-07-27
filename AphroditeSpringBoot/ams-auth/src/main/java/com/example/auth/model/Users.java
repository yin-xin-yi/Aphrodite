package com.example.auth.model;

/**
 * import jakarta.persistence.*; 就是 Java 程序与关系型数据库（如 PostgreSQL,
 * MySQL）之间的“官方翻译官”和“外交协议” 就是 下面那些 各种 @ @ @ @ @
 *
 * import lombok.AllArgsConstructor;
 *
 * @AllArgsConstructor 这个就是 帮助 自动生成构造函数的
 *
 * @Data 是一个组合注解。当你给一个类加上 @Data，Lombok 会在编译时自动帮你生成以下五个核心注解的功能：
 * @Getter：为所有字段生成 get...() 方法。
 * @Setter：为所有字段生成 set...() 方法。
 * @ToString：生成一个非常智能和可读的 toString() 方法。
 * @EqualsAndHashCode：生成 equals() 和 hashCode() 方法，用于比较两个对象是否相等。
 * @RequiredArgsConstructor：生成一个包含所有被 final 或 @NonNull 标记的字段的构造函数
 *
 * @NoArgsConstructor 自动构造 啥也不用干
 *
 * @ CreationTimestamp 自动在实体第一次被保存到数据库时，将当前时间戳设置到被注解的字段上
 *
 * OffsetDateTime 这个是 现代时间地标准类
 */
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
