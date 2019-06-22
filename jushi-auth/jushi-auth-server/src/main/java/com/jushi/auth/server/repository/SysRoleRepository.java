package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysRole;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wangyunfei on 2017/6/9.
 */
public interface SysRoleRepository extends MongoRepository<SysRole,String> {
}