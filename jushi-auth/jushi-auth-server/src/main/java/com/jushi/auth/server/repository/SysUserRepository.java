package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SysUserRepository extends ReactiveMongoRepository<SysUser,String> {
    Mono<SysUser> findOneWithRolesByUsername(String username);
}