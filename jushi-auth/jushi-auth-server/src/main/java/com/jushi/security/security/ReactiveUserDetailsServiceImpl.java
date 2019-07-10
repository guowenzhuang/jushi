package com.jushi.security.security;

import com.jushi.security.domain.SysUser;
import com.jushi.security.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * @author 80795
 * @date 2019/7/2 20:29
 */
@Component
@Slf4j
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserRepository.findOneWithRolesByUsername(username)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BadCredentialsException(String.format("User %s not found in database", username))))
                .map(this::createSpringSecurityUser);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(SysUser user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
