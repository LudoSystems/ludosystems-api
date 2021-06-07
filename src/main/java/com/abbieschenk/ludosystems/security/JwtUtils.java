package com.abbieschenk.ludosystems.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for working with jwt authentication.
 *
 * @author abbie
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${ludobaum.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ludobaum.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Generates a JWT token for the passed in authentication.
     *
     * @param authentication The Authentication to create a JWT token for.
     * @return A JWT token.
     */
    public String generateJwtToken(Authentication authentication) {
        final UserDetails userPrincipal;
        final byte[] keyBytes;
        final Key key;
        final Date issueDate;
        final Date expiryDate;

        userPrincipal = (UserDetails) authentication.getPrincipal();
        keyBytes = Decoders.BASE64.decode(jwtSecret);
        key = Keys.hmacShaKeyFor(keyBytes);
        issueDate = new Date();
        expiryDate = new Date(issueDate.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Parses out the username from a jwt token.
     *
     * @param token The JWT token.
     * @return A String representing the username.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Check if the passed in JWT token is valid. Logs to the error level
     * if there is an exception.
     *
     * @param token The JWT token
     * @return true if the token was valid, false if not.
     */
    public boolean validateJwtToken(String token) {
        boolean valid = false;

        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            valid = true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return valid;
    }
}
