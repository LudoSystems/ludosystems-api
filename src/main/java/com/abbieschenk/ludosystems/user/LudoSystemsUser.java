package com.abbieschenk.ludosystems.user;

import com.abbieschenk.ludosystems.attributelist.AttributeList;
import com.abbieschenk.ludosystems.node.Node;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="ludosystems.ludosystems_user")
public class LudoSystemsUser implements UserDetails {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private LudoSystemsUserRole role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Node> nodes = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<AttributeList> attributeLists = new HashSet<>();

    protected LudoSystemsUser() {
    }

    public LudoSystemsUser(String name, String email, String password, LudoSystemsUserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;

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
        return Collections.singleton(this.role);
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

    public LudoSystemsUserRole getRole() {
        return role;
    }

    public void setRole(LudoSystemsUserRole role) {
        this.role = role;
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
        LudoSystemsUser user = (LudoSystemsUser) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, role);
    }

    @Override
    public String toString() {
        return "LudoSystemsUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + role +
                '}';
    }

    public Set<AttributeList> getAttributeLists() {
        return attributeLists;
    }

    public void setAttributeLists(Set<AttributeList> attributeLists) {
        this.attributeLists = attributeLists;
    }
}
