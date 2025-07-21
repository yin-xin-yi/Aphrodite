package com.example.amsregister.utils;

// slf4j Java 简单日志门面
// java.nio 非阻塞 I/O的包
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;

// @Component：通用组件
// @Component 是 “员工”
// @Service 是 “工程师”
// @Repository 是 “仓库管理员”
// @Controller 是 “前台接待”
// 
@Component
public class TokenUtil {

    // ————————————————————————————————————————————————————————————————————————
    // 成员变量
    // 领取一本 工作日志本
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // 加密和解密 用的是一把钥匙 都是 这个 SecretKey
    private SecretKey key;
    // ————————————————————————————————————————————————————————————————————————

    // ————————————————————————————————————————————————————————————————————————
    /**
     * PostConstruct 这个注解的作用： 自动地调用下面这个 init() 方法。这个操作只做一次 加密算法 操作字节 getBytes
     * 这可以获取对应的字节 StandardCharsets.UTF_8 通用编码的方式 import
     * java.nio.charset.StandardCharsets;
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        logger.info("JWT SecretKey 初始化完成。");
    }

    /**
     * 生成 token
     *
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        logger.info("为用户 '{}' 生成 Token,有效期至: {}", username, expiryDate);
        return Jwts.builder()
                .setSubject(username) // 将用户名作为 Token 的主题
                .setIssuedAt(now) // 设置签发时间
                .setExpiration(expiryDate) // 设置过期时间
                .signWith(key, SignatureAlgorithm.HS512) // 使用我们的“钥匙”和HS512算法进行签名
                .compact(); // 构建并压缩成一个 URL 安全的字符串
    }

    /**
     * 验证token 是不是有效的
     *
     */
    public boolean validateToken(String token, String username) {
        try {
            final String usernameFromToken = getUsername(token);
            return (usernameFromToken.equals(username));
        } catch (SignatureException e) {
            logger.error("JWT 签名验证失败: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("无效的 JWT token 格式: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token 已过期: {}", e.getMessage()); // 过期是正常情况，用 warn 级别
        } catch (UnsupportedJwtException e) {
            logger.error("不支持的 JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims 字符串为空或不合法: {}", e.getMessage());
        }
        // 如果捕获到任何异常，说明 token 无效
        return false;
    }

    /**
     * 获取用户名
     */
    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // ————————————————————————————————————————————————————————————————————————

    // ————————————————————————————————————————————————————————————————————————
    // 私有成员函数
    /**
     *
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 获取 token 里面的 Claims 对象
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder() // Jwts.parserBuilder() 线程安全
                .setSigningKey(key)
                .build() // 线程安全的 JwtParser 实例
                .parseClaimsJws(token) // 判断一下是不是合法的
                .getBody(); // 提取载荷部分，即 Claims 对象
    }
    // ————————————————————————————————————————————————————————————————————————

}
