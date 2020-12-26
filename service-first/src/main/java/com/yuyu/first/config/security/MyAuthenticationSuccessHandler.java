package com.yuyu.first.config.security;

import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        DefaultSavedRequest savedRequest = (DefaultSavedRequest)requestCache.getRequest(request, response);

        clearAuthenticationAttributes(request);

        response.setStatus(HttpStatus.SC_OK);

        if (savedRequest != null) {
            requestCache.removeRequest(request, response);
            //or json response.setContentType("application/json;charset=utf=8");
            String sb = savedRequest.getRequestURI();
            response.getWriter().print(sb);
        }
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
