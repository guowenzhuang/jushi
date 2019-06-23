package com.jushi.article.repository;

import com.jushi.api.pojo.po.ArticlePO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * 板块dao
 * @author 80795
 */
public interface ArticleRepository extends ReactiveMongoRepository<ArticlePO,String> {


}
