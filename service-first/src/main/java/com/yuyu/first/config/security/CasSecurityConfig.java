package com.yuyu.first.config.security;

import com.yuyu.first.config.security.properties.CasClientProperties;
import com.yuyu.first.config.security.properties.CasServerProperties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.ArrayList;


@Configuration
@EnableConfigurationProperties
public class CasSecurityConfig {

    @Autowired
    CasClientProperties casClientProperties;
    @Autowired
    CasServerProperties casServerProperties;
    @Autowired
    UserDetailsService userDetailService;

    @Bean
    ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casClientProperties.getLogin());
        return serviceProperties;
    }

    @Bean
    @Primary
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }


    @Bean
    TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(casServerProperties.getPrefix());
    }

    @Bean
    CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());
        provider.setUserDetailsService(userDetailService);
        provider.setKey("key4CasAuthenticationProvider");
        return provider;
    }

    @Bean
    CasAuthenticationFilter casAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        ArrayList<AuthenticationProvider> list = new ArrayList<>();
        list.add(authenticationProvider);
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setAuthenticationManager(new ProviderManager(list));
        filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        filter.setFilterProcessesUrl(casClientProperties.getLoginRelative());
        return filter;
    }

    @Bean
    SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter sign = new SingleSignOutFilter();
        sign.setIgnoreInitConfiguration(true);
        return sign;
    }

    @Bean
    LogoutFilter logoutFilter() {
        LogoutFilter filter = new LogoutFilter(casServerProperties.getLogout(), new SecurityContextLogoutHandler());
        filter.setFilterProcessesUrl(casClientProperties.getLogoutRelative());
        return filter;
    }
}
