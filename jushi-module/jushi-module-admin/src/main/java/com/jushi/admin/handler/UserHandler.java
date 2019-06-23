package com.jushi.admin.handler;

import com.jushi.admin.repository.UserRepository;
import com.jushi.api.exception.CheckException;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.api.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
public class UserHandler {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> userRegister(ServerRequest request) {
        Mono<SysUserPO> userMono = request.bodyToMono(SysUserPO.class);
        return userMono.flatMap(u -> {
            userRegisterCheck(u);
            SysUserPO sysUserName = SysUserPO
                    .builder()
                    .username(u.getUsername())
                    .build();
            Example<SysUserPO> example = Example.of(sysUserName);
            log.info("查询是否有重名用户 入参为 username:{}", sysUserName.getUsername());
            return userRepository.exists(example).flatMap(is -> {
                if (is) {
                    throw new CheckException("用户", sysUserName.getUsername(), "此用户已存在");
                }

                u.setPassword(encoder.encode(u.getPassword()))
                        .setCreatedBy(u.getUsername())
                        .setCreatedDate(new Date());
                Mono<SysUserPO> saveUser = userRepository.save(u);
                return saveUser.flatMap(saUser -> {
                    return ServerResponse.ok()
                            .body(Mono.just(Result.success("创建用户成功",saUser))
                                    , Result.class);
                });

            });
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error("注册用户不为null")), Result.class));
    }

    private void userRegisterCheck(SysUserPO sysUserPO) {
        log.info("用户信息校验");
        CheckUtil.checkEmpty("用户名", sysUserPO.getUsername());
        CheckUtil.checkEmpty("密码", sysUserPO.getPassword());
    }


}
