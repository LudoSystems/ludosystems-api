package com.abbieschenk.ludobaum.user;

import com.abbieschenk.ludobaum.node.Node;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class LudobaumUser implements UserDetails {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private LudobaumUserRole role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Node> nodes = new HashSet<>();

    @Transient
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    protected LudobaumUser() {
    }

    public LudobaumUser(String name, String email, String password, LudobaumUserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.authorities.add(role);

        this.setPassword(password);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        if (this.authorities.isEmpty()) {
            this.authorities.add(this.role);
        }
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LudobaumUserRole getRole() {
        return role;
    }

    public void setRole(LudobaumUserRole role) {
        this.role = role;

        this.authorities.clear();
        this.authorities.add(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LudobaumUser ludobaumUser = (LudobaumUser) o;
        return Objects.equals(id, ludobaumUser.id) &&
                Objects.equals(name, ludobaumUser.name) &&
                Objects.equals(password, ludobaumUser.password) &&
                Objects.equals(email, ludobaumUser.email) &&
                Objects.equals(role, ludobaumUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + role +
                '}';
    }
}
