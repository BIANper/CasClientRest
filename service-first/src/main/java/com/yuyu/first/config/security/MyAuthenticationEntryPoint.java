package com.yuyu.first.config.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public final void commence(HttpServletRequest servletRequest,
                               HttpServletResponse response,
                               AuthenticationException authenticationException) {

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);

    }
}
