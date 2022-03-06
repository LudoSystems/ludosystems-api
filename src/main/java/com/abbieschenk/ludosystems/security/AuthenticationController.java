package com.abbieschenk.ludosystems.security;

import com.abbieschenk.ludosystems.user.LudoSystemsUser;
import com.abbieschenk.ludosystems.user.LudoSystemsUserRole;
import com.abbieschenk.ludosystems.user.LudoSystemsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AuthenticationController.PATH)
public class AuthenticationController {

    public static final String PATH = "/auth";

    private final AuthenticationManager authenticationManager;
    private final LudoSystemsUserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    LudoSystemsUserService userService,
                                    JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        final Authentication auth;
        final String jwt;
        final LudoSystemsUser user;
        final List<String> roles;

        auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        jwt = jwtUtils.generateJwtToken(auth);
        user = (LudoSystemsUser) auth.getPrincipal();
        roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
        final ResponseEntity<?> response;

        if(true) {
            return ResponseEntity.badRequest().body("Sorry, ludo.systems is not online right now.");
        }
        
        if (userService.getUserByName(request.getUsername()) != null) {
            response = ResponseEntity.badRequest().body("Error: A user already exists with this name.");
        } else if (userService.getUserByEmail(request.getEmail()) != null) {
            response = ResponseEntity.badRequest().body("Error: A user already exists with this email.");
        } else {
            final LudoSystemsUser user;

            user = new LudoSystemsUser(request.getUsername(), request.getEmail(), request.getPassword(),
                    LudoSystemsUserRole.USER);

            userService.addUser(user);

            response = ResponseEntity.ok("LudoSystemsUser registered.");
        }

        return response;
    }

    @SuppressWarnings("unused")
    private static class JwtResponse {
        private static final String TYPE = "Bearer";

        private final String token;
        private final Long id;
        private final String username;
        private final String email;
        private final List<String> roles;

        private JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
            this.token = accessToken;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }

        public String getToken() {
            return token;
        }

        public String getTokenType() {
            return TYPE;
        }

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public List<String> getRoles() {
            return roles;
        }
    }
}
