package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysUser;
import com.jushi.auth.server.repository.support.WiselyRepository;

import java.util.Optional;

public interface SysUserRepository extends WiselyRepository<SysUser,Long> {
    Optional<SysUser> findOneWithRolesByUsername(String username);
}