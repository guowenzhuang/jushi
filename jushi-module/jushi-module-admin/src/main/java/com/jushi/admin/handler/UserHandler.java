package com.jushi.admin.handler;

import cn.hutool.core.util.StrUtil;
import com.jushi.admin.pojo.dto.ChangePassDTO;
import com.jushi.admin.pojo.dto.RegisterUserDTO;
import com.jushi.admin.repository.UserRepository;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.api.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
public class UserHandler extends BaseHandler<UserRepository, SysUserPO> {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    /**
     * 修改密码
     *
     * @param request
     * @return
     */

    public Mono<ServerResponse> changePassword(ServerRequest request) {
        Mono<ChangePassDTO> userPasswordChange = request.bodyToMono(ChangePassDTO.class);
        return userPasswordChange.flatMap(upc -> {
            Example<SysUserPO> exampleUser = Example.of(
                    SysUserPO.builder()
                            .username(upc.getUsername())
                            .build());
            Mono<SysUserPO> user = userRepository.findOne(exampleUser);
            return user.flatMap(u -> {
                if (!encoder.matches(upc.getOldPassword(), u.getPassword())) {
                    throw new CheckException("修改密码", upc.getOldPassword(), "原密码错误");
                }
                // 加密密码
                u.setPassword(encoder.encode(upc.getNewPassword()));
                return userRepository.save(u).flatMap(saveU ->{
                    return ServerResponse.ok().body(Mono.just(Result.success("修改密码成功",saveU)),Result.class);
                });
            }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("此用户不存在", upc.getUsername()))), Result.class));

        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 参数不能为null", RegisterUserDTO.class.getName()))), Result.class));
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getCurrentUser(ServerRequest request) {
        Mono<UserDetails> currentUser = getCurrentUser();
        return currentUser.flatMap(item -> {
            String username = item.getUsername();
            Example<SysUserPO> exampleUser = Example.of(SysUserPO.builder().username(username).build());
            Mono<SysUserPO> user = userRepository.findOne(exampleUser);
            return user.flatMap(u -> {
                //FIXME 返回的时候不需要密码 需要改为实体类DTO返回
                u.setPassword("");
                return ServerResponse.ok().body(Mono.just(u), SysUserPO.class);
            });

        });
    }

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> userRegister(ServerRequest request) {
        Mono<RegisterUserDTO> userMono = request.bodyToMono(RegisterUserDTO.class);
        return userMono.flatMap(u -> {
            //检查必填字段
            userRegisterCheck(u);
            SysUserPO sysUserName = SysUserPO
                    .builder()
                    .username(u.getUsername())
                    .build();
            Example<SysUserPO> example = Example.of(sysUserName);
            //查询名称是否已有
            log.info("查询是否有重名用户 入参为 username:{}", sysUserName.getUsername());
            return userRepository.exists(example).flatMap(is -> {
                if (is) {
                    throw new CheckException("用户", sysUserName.getUsername(), "此用户已存在");
                }
                //RegisterUserDTO 转换成 sysUserPO
                SysUserPO sysUserPO = new SysUserPO();
                BeanUtils.copyProperties(u, sysUserPO);
                //加密信息
                sysUserPO.setPassword(encoder.encode(sysUserPO.getPassword()))
                        .setCreatedBy(sysUserPO.getUsername())
                        .setCreatedDate(new Date());
                //保存用户
                Mono<SysUserPO> saveUser = userRepository.save(sysUserPO);
                return saveUser.flatMap(saUser -> {
                    return ServerResponse.ok()
                            .body(Mono.just(Result.success("注册成功", saUser))
                                    , Result.class);
                });

            });
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error("注册用户不为null")), Result.class));
    }

    private void userRegisterCheck(RegisterUserDTO registerUserDTO) {
        //FIXME 检验不通过 应该返回 错误 不应该走全局异常处理 否则会造成提示信息不明确
        log.info("用户信息校验");
        CheckUtil.checkEmpty("用户名", registerUserDTO.getUsername());
        CheckUtil.checkEmpty("密码", registerUserDTO.getPassword());
    }

    private Mono<UserDetails> getCurrentUser() {
        Mono<UserDetails> user = ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(item -> (UserDetails) item);
        return user;
    }

}





