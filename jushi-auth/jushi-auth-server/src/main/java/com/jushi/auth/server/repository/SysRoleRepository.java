package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by wangyunfei on 2017/6/9.
 */
public interface SysRoleRepository extends ReactiveMongoRepository<SysRole,String> {
}