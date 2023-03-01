package com.gsd.common.core.domain.model;

import com.gsd.common.core.domain.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class LoginUser implements UserDetails {
    private SysUser user;
    private String token;
    private Set<String> permissions;
    private Long userId;

    public LoginUser(Long userId,SysUser user, Set<String> permissions) {
        this.userId = userId;
        this.user=user;
        this.permissions = permissions;

    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(String permission:permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SysUser getUser() {
        return user;
    }

    public Long getUserId() {
        return userId;
    }
}
