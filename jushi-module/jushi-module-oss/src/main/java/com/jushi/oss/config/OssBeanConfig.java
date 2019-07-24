package com.jushi.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jushi.oss.pojo.consts.OssConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssBeanConfig {

    @Bean
    public OSS gtOSS() {
        return new OSSClientBuilder().build(OssConst.SPRING_OSS_ENDPOINT,
                OssConst.SPRING_OSS_ACCESS_KEY_ID,
                OssConst.SPRING_OSS_ACCESS_KEY_SECRET);
    }
}
