package com.jushi.security.repository;

import com.jushi.security.domain.SysAuthority;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by wangyunfei on 2017/6/14.
 */
public interface SysAuthotityRepository extends ReactiveMongoRepository<SysAuthority,String> {
}