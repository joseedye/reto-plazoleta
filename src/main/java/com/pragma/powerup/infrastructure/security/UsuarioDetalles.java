package com.pragma.powerup.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalles implements UserDetails {

    private final Long id;
    private final String username;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;

    public UsuarioDetalles(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
    }


    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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
}
