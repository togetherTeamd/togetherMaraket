package com.project.together.config.auth;

import com.project.together.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) { this.user = user; }

    /**
     * 해당 유저의 권한을 가져오는 메소드
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    /**
     * 비밀번호를 가져오는 메소드
     * @return
     */
    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    /**
     * 유저 id를 가져오는 메소드
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    /**
     * 계정 만료 확인
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠금 확인
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 계정 비밀번호 변경 확인
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 확인
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
