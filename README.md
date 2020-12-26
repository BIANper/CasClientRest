SpringSecurity + CAS

# 大概思路

认证器：

使用 `org.springframework.security.cas` 提供的 CasAuthenticationProvider 。

认证异常：

自定义一个 AuthenticationEntryPoint，如果用 CasAuthenticationEntryPoint 直接 `response.sendRedirect(redirectUrl);` 去 CAS Service 登录页了。

拦截器：

使用 CasAuthenticationFilter，设置自定义的 SuccessHandler，将原本的重定向逻辑重写为返回 200。



# Session 一致性

通过 Redis 共享 Session。

PS Spring Cloud Gateway 基于 WebFlux 实现，不过 Spring Session 已经支持。[@EnableRedisWebSession](https://docs.spring.io/spring-session/docs/2.4.1/reference/html5/#websession)



# 用到的依赖

```xml
<!-- 鉴权 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!-- 认证 -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-cas</artifactId>
</dependency>
<!-- Session一致性 -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

