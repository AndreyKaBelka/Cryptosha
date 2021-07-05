package com.messenger.cryptosha;

import com.messenger.cryptosha.exceptions.InvalidJwtAuthenticationException;
import com.messenger.cryptosha.resource.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider tokenProvider;
    private final HandlerExceptionResolver resolver;

    public JwtTokenFilter(JwtTokenProvider tokenProvider, HandlerExceptionResolver resolver) {
        this.tokenProvider = tokenProvider;
        this.resolver = resolver;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) request);
        try {
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (InvalidJwtAuthenticationException e) {
            resolver.resolveException(((HttpServletRequest) request), ((HttpServletResponse) response), null, e);
        }
        filterChain.doFilter(request, response);
    }
}
