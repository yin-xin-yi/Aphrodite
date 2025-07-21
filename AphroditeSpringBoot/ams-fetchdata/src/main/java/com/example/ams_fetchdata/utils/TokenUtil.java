package com.example.ams_fetchdata.utils;

// slf4j Java 简单日志门面
// java.nio 非阻塞 I/O的包
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    // 业务函数 public 函数
    /**
     * PostConstruct 这个注解的作用： 自动地调用下面这个 init() 方法这个操作只做一次 加密算法 操作字节 getBytes
     * 这可以获取对应的字节 StandardCharsets.UTF_8 通用编码的方式 import
     * java.nio.charset.StandardCharsets;
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        logger.info("JWT SecretKey 初始化完成");
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
     * 从 Token 中提取用户名 如果 Token 无效（过期、签名错误等），此方法会抛出异常
     *
     * @param token JWT
     * @return 用户名
     */
    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 验证 Token 对于指定用户是否有效 这个方法是高效的，因为它假定你已经通过 extractUsername 成功解析出了用户名
     * 所以它只需要再检查是否过期即可
     *
     * @param token JWT
     * @param userDetails 从数据库加载的用户详情
     * @return 如果用户名匹配且 Token 未过期，则返回 true
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // 关键：检查用户名是否匹配，并且 Token 没有过期
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 检查 Token 是否过期
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 从 Token 中提取过期时间
     */
    private Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
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
