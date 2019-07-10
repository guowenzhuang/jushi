# 巨石论坛

## 技术栈

本次项目基于spring Cloud 2.0.6

采用spring Webflux handler模式开发

| 名称               | 介绍                        | 链接                                                                                                                  |
| ---------------- | ------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| gateway          | spring cloud 提供的路由        | https://spring.io/projects/spring-cloud-gateway                                                                     |
| nacos            | alibaba 提供的服务注册和发现 & 配置中心 | https://nacos.io/zh-cn/index.html                                                                                   |
| mongodb-reactive | mongodb-reactive作为dao层框架  | https://www.baeldung.com/spring-data-mongodb-reactive                                                               |
| data-redis       | 操作redis的框架                | https://spring.io/projects/spring-data-redis                                                                        |
| lombok           | 帮助快速生成类方法                 | https://www.projectlombok.org/                                                                                      |
| security         | 用户认证                      | https://spring.io/projects/spring-cloud-security                                                                    |
| webflux          | 反应式编程                     | https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html |

数据库采用mongodb4..0.10作为主数据库 redis 作为缓存数据库

## 为什么要采用mongodb作为主数据库

1. mongodb支持 reactive (mysql 不支持 本来打算采用mysql)

2. mongodb与关系型数据库结构最为接近 耗费学习成本较小

3. mongodb在4.0 版本后新增了事务 缩减了与关系型数据库的差距

4. mongodb在2.4版本后已经修复了数据丢包问题

## 模块说明

```
jushi   
| 
--jushi-auth                    权限
|
----jushi-auth-client         权限客户端 由其他模块集成
|
----jushi-auth-common         权限公共模块  权限公共的依赖
|
----jushi-auth-server         权限服务端
|
--jushi-gateway                网关
|
--jushi-module                 业务模块
|
----jushi-module-admin         后台管理模块
|
----jushi-module-api           业务公共模块
|
----jushi-module-web            前端服务端模块
```

## 如何启动

### 1. 安装依赖环境

> 可以在 application.yml 中修改账号密码等配置

- jdk8

- redis5

- nacos1.0.1 

- mongodb4.0.1

### 2.导入sql脚本(在主目录下的sql目录内)

### 3.拉取项目

### 4.导入依赖

### 5.启动项目

1. jushi-auth-server

2. jushi-gateway

3. jushi-module-web

4. jushi-module-admin

没有先后顺序

### 6. 启动前端

前端链接: [https://github.com/guowenzhuang/jushi-web-vant](https://github.com/guowenzhuang/jushi-web-vant)

## TODO

- admin 后台基本还未开发

- 搜索未集成es

- 评论功能未开放

- ...
