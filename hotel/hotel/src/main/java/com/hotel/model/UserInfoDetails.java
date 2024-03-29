package com.hotel.model;

import com.hotel.model.entity.Role;
import com.hotel.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
@RequiredArgsConstructor
public class UserInfoDetails implements UserDetails {

    private String name;
    private String password;
    private Integer userId;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User userInfo) {
        name = userInfo.getUsername();
        password = userInfo.getPassword();
        authorities = Arrays.stream(new Role[]{userInfo.getRole()})
                .map((Role role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        userId=userInfo.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

