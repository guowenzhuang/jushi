<p align="center">
  <a href="" rel="noopener">
 <img width=200px height=200px src="./logo.png" alt="Project logo"></a>
</p>

<h3 align="center">巨石论坛</h3>


---

<p align="center"> 
    一个可以闲聊和讨论技术的论坛
    <br> 
</p>

## 📝 目录
- [介绍](#about)
- [运行](#run)
- [项目描述](#jushiDetail)
- [技术栈](#skill)
- [TODO](#todo)
- [作者](#author)
- [致谢](#thank)

## 🧐 介绍 <a name = "about"></a>
巨石论坛:后端采用java微服务,webflux模式开发 前端采用vue开发的论坛

## 🏁 安装 <a name = "run"></a>

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



## 🔧 项目描述 <a name = "jushiDetail"></a>
### 目录结构
```
|-- jushi                                 
    |-- jushi-auth                     权限模块
    |   |-- jushi-auth-client            权限客户端
    |   |-- jushi-auth-common            权限公共模块
    |   |-- jushi-auth-server            权限服务端
    |-- jushi-gateway                  网关
    |-- jushi-module                   业务模块
    |   |-- jushi-module-admin           后台管理
    |   |-- jushi-module-api             业务公共模块
    |   |-- jushi-module-web             web后台
    |-- sql                            sql脚本

```
### 项目介绍
项目采用微服务的架构并结合流行的webflux模式开发

抛弃了原先的 controller service dao 分层开发
采用route handler dao分层开发 全部是基于函数式响应式的开发

### 数据库
本次项目采用mongodb作为主数据库 主要有以下几种原因
1. mongodb支持 reactive(响应式)

2. mongodb与关系型数据库结构最为接近 耗费学习成本较小

3. mongodb在4.0 版本后新增了事务 解决了不如关系型数据库的缺点

4. mongodb在2.4版本后已经修复了数据丢包问题


## 🎈 技术栈 <a name="skill"></a>
| 名称               | 介绍                        | 链接                                                                                                                  |
| ---------------- | ------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| gateway          | spring cloud 基于webflux的路由        | https://spring.io/projects/spring-cloud-gateway                                                                     |
| nacos            | 服务注册和发现 & 配置中心 | https://nacos.io/zh-cn/index.html                                                                                   |
| mongodb-reactive | mongodb-reactive作为dao层框架  | https://www.baeldung.com/spring-data-mongodb-reactive                                                               |
| data-redis       | 操作redis的框架                | https://spring.io/projects/spring-data-redis                                                                        |
| lombok           | 帮助快速生成开发的插件                 | https://www.projectlombok.org/                                                                                      |
| security         | 用户认证                      | https://spring.io/projects/spring-cloud-security                                                                    |
| webflux          | 反应式编程                     | https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html |


## 🚀 TODO <a name = "todo"></a>
- admin 后台管理未开发
- 搜索未集成es
- 文章显示 增加内容和图片显示
- 搜索中文分词 hutool DFA查找
- 登录 注册 修改密码 新增验证码校验
- 文章 由base64上传改为文件上传 mongodb gridfs
- 个人中心(修改密码,修改头像,查看自己的贴子,点赞)
- 可以给文章和评论点赞
- 下方导航图标字体修改
- 下方导航要固定在下方

## ✍️ 作者 <a name = "authors"></a>
- [@gwz](https://guowenzhuang.gitee.io/boke/) 

## 🎉 致谢 <a name = "thank"></a>
- [@qym]() 

