package com.jushi.article.initdata;

import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.PlatePO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.article.repository.ArticleRepository;
import com.jushi.article.repository.PlateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;


/**
 * @author 80795
 * @date 2019/6/23 13:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DBDataInit {
    @Autowired
    private PlateRepository plateRepository;
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 初始化板块数据
     */
    @Test
    public void initPlate() {
        if (Boolean.FALSE)
            Flux.just(PlatePO
                            .builder()
                            .name("技术")
                            .clickCount(0L)
                            .topicCount(0L)
                            .weight(99L)
                            .state(Boolean.TRUE)
                            .build(),
                    PlatePO
                            .builder()
                            .name("闲聊")
                            .clickCount(0L)
                            .topicCount(0L)
                            .weight(50L)
                            .state(Boolean.TRUE)
                            .build(),
                    PlatePO
                            .builder()
                            .name("内推招聘")
                            .clickCount(0L)
                            .topicCount(0L)
                            .weight(20L)
                            .state(Boolean.TRUE)
                            .build(),
                    PlatePO
                            .builder()
                            .name("相亲")
                            .clickCount(0L)
                            .topicCount(0L)
                            .weight(10L)
                            .state(Boolean.TRUE)
                            .build()).flatMap(plate -> {
                return plateRepository.save(plate).flatMap(savePlate -> {
                    return Mono.just(ArticlePO
                            .builder()
                            .title(savePlate.getName() + " 测试标题")
                            .plate(savePlate)
                            .build()).flatMap(articlePO -> {
                        return articleRepository.save(articlePO);
                    });

                });
            }).subscribe();
    }

    @Test
    public void initArticle() {
        if (Boolean.FALSE)
            for (int i = 0; i < 10; i++)
                Flux.just(
                        ArticlePO
                                .builder()
                                .title("学技术哪家强")
                                .content("新东方新东方新东方新东方新东方新东方新东方")
                                .cover("新东方")
                                .isImage(Boolean.FALSE)
                                .createTime(new Date())
                                .isPublic(Boolean.TRUE)
                                .isTop(Boolean.FALSE)
                                .isPopular(Boolean.FALSE)
                                .scanCount(0L)
                                .likeCount(0L)
                                .commentCount(0L)
                                .auditState(Boolean.TRUE)
                                .sysUser(SysUserPO.builder().id("5d0db24cb5709c0a2879dee1").build())
                                .plate(PlatePO.builder().id("5d0f76a4e60170361824b2ba").build())
                                .build()).flatMap(articlePO -> {
                    return articleRepository.save(articlePO);
                }).subscribe();
    }


}
