package com.jushi.auth.server.repository;

import com.jushi.auth.server.domain.SysAuthority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by wangyunfei on 2017/6/14.
 */
public interface SysAuthotityRepository extends ReactiveMongoRepository<SysAuthority,String> {
}