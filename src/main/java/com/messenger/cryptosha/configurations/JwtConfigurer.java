package com.messenger.cryptosha.configurations;

import com.messenger.cryptosha.JwtTokenFilter;
import com.messenger.cryptosha.resource.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver resolver;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, HandlerExceptionResolver resolver) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.resolver = resolver;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, resolver);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
