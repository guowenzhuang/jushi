package com.jushi.web.handler;

import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.po.CommentPO;
import com.jushi.web.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 80795
 * @date 2019/7/7 20:50
 */
@Slf4j
@Component
public class CommentHandler extends BaseHandler<CommentRepository, CommentPO> {

}
