package com.abbieschenk.ludosystems.security;

import com.abbieschenk.ludosystems.user.LudoSystemsUser;
import com.abbieschenk.ludosystems.user.LudoSystemsUserService;
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
    private final LudoSystemsUserService userDetailsService;

    @Autowired
    public AuthenticationTokenFilter(JwtUtils jwtUtils, LudoSystemsUserService userDetailsService) {
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
                final LudoSystemsUser user;
                final UsernamePasswordAuthenticationToken authentication;

                username = jwtUtils.getUserNameFromJwtToken(jwt);
                user = userDetailsService.loadUserByUsername(username);

                if(user != null) {
                    authentication = new UsernamePasswordAuthenticationToken(user, null,
                            user.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
