package com.example.ams_fetchdata.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 注意：jjwt 0.11.x 之后，签名异常在这个包下
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ams_fetchdata.utils.TokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * 1. @Slf4j OncePerRequestFilter 它会自动在类中创建一个名为 log 的日志记录器，你就可以直接使用 log.info(),
 * log.error() 等方法 2. extends ... 这个就是让这个类称为 过滤器 这个保证 每一次的请求 都会调一次这个类
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;

    /**
     * 1. NonNull 这里是强调 这三个参数 是tomcat的 不会是空的
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 基本的 头应该是 Authorization
        // 然后里面应该是 Bearer
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 去掉 Bearer
        final String jwt = authHeader.substring(7);
        String username = null;
        try {
            username = tokenUtil.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            log.warn("JWT token 已过期: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT 签名无效: {}", e.getMessage());
        } catch (Exception e) {
            log.error("无效的 JWT token: {}", e.getMessage());
        }

        // 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 这里验证一下 名字都对的上
            if (tokenUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // 这里本身是密码 但是 jwt本身就可以验证
                        userDetails.getAuthorities()
                );
                // 把当前请求的一些额外信息（如 IP 地址、Session ID）存入凭证，方便后续审计
                // WebAuthenticationDetailsSource 会从 HttpServletRequest 中提取远程 IP 地址和 Session ID
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 无论如何，都继续过滤器链
        filterChain.doFilter(request, response);
    }
}
