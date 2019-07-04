package com.jushi.security.repository;

import com.jushi.security.domain.SysRole;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by wangyunfei on 2017/6/9.
 */
public interface SysRoleRepository extends ReactiveMongoRepository<SysRole,String> {
}