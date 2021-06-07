package com.stapply.backend.stapply.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null) {

            try {
               if(jwtTokenProvider.validateToken(token)) {
                   var auth = jwtTokenProvider.getAuthentication(token);

                   if (auth != null) {
                       SecurityContextHolder.getContext().setAuthentication(auth);
                   }
               }
            } catch (JwtAuthenticationException ignored) {}
        }
        chain.doFilter(request, response);
    }
}
