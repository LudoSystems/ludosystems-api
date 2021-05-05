package com.abbieschenk.ludobaum.security;

import com.abbieschenk.ludobaum.user.LudobaumUser;
import com.abbieschenk.ludobaum.user.LudobaumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final LudobaumUserService userDetailsService;

    @Autowired
    public AuthenticationTokenFilter(JwtUtils jwtUtils, LudobaumUserService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            final String jwt = authHeader.substring(7);

            if (jwtUtils.validateJwtToken(jwt)) {
                final String username;
                final LudobaumUser user;
                final UsernamePasswordAuthenticationToken authentication;

                username = jwtUtils.getUserNameFromJwtToken(jwt);
                user = userDetailsService.loadUserByUsername(username);
                authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
