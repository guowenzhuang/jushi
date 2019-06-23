package com.jushi.article.repository;

import com.jushi.api.pojo.po.PlatePO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * 板块dao
 * @author 80795
 */
public interface PlateRepository extends ReactiveMongoRepository<PlatePO,String> {


}
