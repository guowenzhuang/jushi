package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SysUserRepository extends MongoRepository<SysUser,String> {
    Optional<SysUser> findOneWithRolesByUsername(String username);
}