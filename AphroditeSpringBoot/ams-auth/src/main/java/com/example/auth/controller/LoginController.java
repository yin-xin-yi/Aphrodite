package com.example.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.Data;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.model.Users;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.PasswordService;
import com.example.auth.utils.TokenUtil;

/**
 * RestController 这个是
 *
 * 1. @Controller：首先，它告诉 Spring 这就是一个控制器（Controller） 是用来接收和处理网页请求的组件 请 Spring
 * IoC 容器来管理它
 *
 * 2. @ResponseBody：然后，它为这个类里的所有方法都加上了 @ResponseBody 功能
 * 它意味着方法的返回值不会被解析成一个页面（比如跳转到某个 HTML） 而是被直接转换成数据格式（通常是 JSON） 并写入到 HTTP
 * 响应体中返回给客户端
 *
 */
/**
 * @RequestMapping 为控制器里的所有方法设置了一个公共的、基础的访问路径前缀
 *
 *
 */
/**
 * @GetMapping("/all")
 *
 * 1. 类路径：/api/users 方法路径：/all 完整路径: /api/users/all 请求方式: GET
 *
 * 2. 必须使用 HTTP GET 方法来请求
 *
 * @PostMapping: 用于处理 POST 请求（通常用于“新建”资源）。
 * @PutMapping: 用于处理 PUT 请求（通常用于“更新”整个资源）。
 * @DeleteMapping: 用于处理 DELETE 请求（通常用于“删除”资源）。
 * @PatchMapping: 用于处理 PATCH 请求（通常用于“部分更新”资源）。
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenUtil tokenservice;

    @Autowired

    public LoginController(UserRepository userRepository, PasswordService passwordService, TokenUtil token) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.tokenservice = token;
    }

    @PostMapping("/login")
    // ResponseEntity Spring 框架专门用来封装整个 HTTP 响应的类 
    public LoginResponse<Data> authenticateUser(@RequestBody LoginRequest loginRequest) {

        /**
         * Optional 1. isPresent() 返回 true：有东西 反之亦然 2. get() 强行拿出 东西 3. orElse(T
         * other) 如果盒子是空的，就给我一个默认的东西 Users user = userOptional.orElse(new
         * Users()); // 如果没找到用户，就返回一个新创建的空用户对象 4. ifPresent(Consumer<T> action)
         * userOptional.ifPresent(user -> { // 这段代码只有在 userOptional 不为空时才会执行 if
         * (passwordService.checkPassword(loginRequest.getPassword(),
         * user.getPasswordHash())) { // ... 登录成功逻辑 ... } });
         */
        Optional<Users> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (passwordService.checkPassword(loginRequest.getPassword(), user.getPasswordHash())) {
                String token = tokenservice.generateToken(user.getUsername());
                String username = user.getUsername();
                Long userid = user.getId();
                Data loginResponse = new Data(token, username, userid);
                return LoginResponse.success("登录成功!", loginResponse);
            } else {
                return LoginResponse.error(401, "用户名或密码错误");
            }
        } else {
            return LoginResponse.error(401, "用户名或密码错误");
        }
    }
}
