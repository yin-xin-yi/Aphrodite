package com.example.amsregister.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

// @Component：通用组件
// @Component 是 “员工”
// @Service 是 “工程师”
// @Repository 是 “仓库管理员”
// @Controller 是 “前台接待”
// 
@Component
public class TokenService {

    @Value("${app.jwt.secret}")
    private String jwt_secret;

    @Value("${app.jwt.expiration-ms}")
    private String expiration;
    

}
