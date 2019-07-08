package com.jushi.web.repository;

import com.jushi.api.pojo.po.CommentPO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * 板块dao
 * @author 80795
 */
public interface CommentRepository extends ReactiveMongoRepository<CommentPO,String> {


}
