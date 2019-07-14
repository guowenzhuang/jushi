package com.jushi.api.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.FieldNameException;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.query.PageQuery;
import com.jushi.api.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.function.Function;

/**
 * @author 80795
 * @date 2019/6/28 22:31
 */
@Slf4j
public abstract class BaseHandler<Repository extends ReactiveMongoRepository, Entity> {
    @Autowired
    private Repository repository;
    /**
     * mongo模板
     */
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    /**
     * 当前EntityClass
     */
    private final Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    private ServerRequest request;

    /**
     * 新增
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> add(ServerRequest request) {
        //获取参数
        Mono<Entity> entityMono = request.bodyToMono(entityClass);
        return entityMono.flatMap(entity -> {
            //新增
            return ServerResponse.ok().body(repository.save(entity), entityClass);
        })
                //参数为null
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 新增 参数不能为null", entityClass.getName()))), Result.class));
    }

    /**
     * 根据id删除
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> delete(ServerRequest request) {
        //获取路径的id
        String id = request.pathVariable("id");
        if (StrUtil.isBlank(id)) {
            CheckUtil.checkEmpty("id", id);
        }
        return repository.findById(id)
                .flatMap(user -> ServerResponse.ok().body(repository.delete(user), Void.class)
                        //查找不到用户
                        .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("id:{} 找不到此数据", id))), Result.class)));
    }

    /**
     * 根据id修改
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest request) {
        this.request = request;
        String id = request.pathVariable("id");
        if (StrUtil.isBlank(id)) {
            CheckUtil.checkEmpty("id", id);
        }
        Mono<Entity> entityMono = request.bodyToMono(entityClass);
        return entityMono.flatMap(entity -> {
            //设置id
            setId(entity);
            //修改
            return ServerResponse.ok().body(repository.save(entity), entityClass);
        })
                //参数为null
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 修改 参数不能为null", entityClass.getName()))), Result.class));
    }

    /**
     * 根据id查找
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> queryById(ServerRequest request) {
        //获取路径的id
        String id = request.pathVariable("id");
        if (StrUtil.isBlank(id)) {
            CheckUtil.checkEmpty("id", id);
        }
        return ServerResponse.ok().body(repository.findById(id)
                        .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("id:{} 找不到此数据", id))), Result.class))
                , entityClass);
        //查找不到用户
    }


    /**
     * 查找所有
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> queryAll(ServerRequest request) {
        Mono<Long> count = repository.count();
        return count.flatMap(sums -> {
            if (sums.longValue() == 0) {
                return ServerResponse.ok().body(Mono.just(Result.error("无数据")), Result.class);
            }
            Flux entityFlux = repository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
            return ServerResponse.ok().body(entityFlux, entityClass);
        });
    }


    /**
     * 查找所有(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> queryAllSSE(ServerRequest request) {
        Mono<Long> count = repository.count();
        return count.flatMap(sums -> {
            if (sums.longValue() == 0) {
                return sSEReponseBuild(Result.error("无数据"),Result.class);
            }
            Flux<Entity> entityFlux = repository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
            return ServerResponse.ok().body(entityFlux, entityClass);
        });

    }

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> page(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();
        PageQuery pageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), PageQuery.class, false);
        return pageQuery(Mono.just(pageQuery),query ->{
            return getQuery(query);
        }, entityFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(entityFlux, entityClass);
        });
    }

    /**
     * 分页查询(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> pageSSE(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();
        PageQuery pageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), PageQuery.class, false);
        return pageQuery(Mono.just(pageQuery),query ->{
            return getQuery(query);
        }, entityFlux -> {
            return sseReturn(entityFlux);
        });
    }


    protected Mono<ServerResponse> pageQuery(Mono<PageQuery> pageQueryMono,
                                           Function<PageQuery,Query> queryFunction,
                                           Function<Flux<Entity>, Mono<ServerResponse>> returnFunc) {
        return pageQueryMono.flatMap(pageQuery -> {
            checkPage(pageQuery);
            Query query = queryFunction.apply(pageQuery);
            Pageable pageable = getPageable(pageQuery);
            Query with = query.with(pageable);
            Mono<Long> count = reactiveMongoTemplate.count(with, entityClass);
            return count.flatMap(sums -> {
                long size = pageQuery.getPage() * pageQuery.getSize();
                if (sums.longValue() == size) {
                    return sSEReponseBuild(Mono.just(Result.error("无数据")),Result.class);
                }
                //获取数据
                Flux<Entity> entityFlux = reactiveMongoTemplate.find(with, entityClass);
                return returnFunc.apply(entityFlux);
            });
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("分页查询{}参数不能为null", entityClass.getName()))), Result.class));

    }

    protected <T> Mono<ServerResponse> sseReturn(Flux<T> entityFlux,Class<T> clazz){
        Mono<T> lastMono = entityFlux.last();
        Flux<ServerSentEvent> serverSentEventFlux = entityFlux.concatMap(item -> {
            return lastMono.map(last -> {
                ServerSentEvent.Builder<Object> entityBuilder = ServerSentEvent.<Object>builder()
                        .retry(Duration.ofDays(1000))
                        .data(item);

                Object lastId = getId(last);
                Object itemId = getId(item);
                log.info("当前值id:{}  最终id:{}",itemId,lastId);
                //判断是否是最终id
                if (lastId.equals(itemId)) {
                    return entityBuilder
                            .id(getId(item).toString())
                            .build();
                }
                return entityBuilder
                        .build();
            });
        });

        return sSEReponseBuild(serverSentEventFlux, ServerSentEvent.class);
    }
    /**
     * sse返回封装
     */
    protected Mono<ServerResponse> sseReturn(Flux<Entity> entityFlux) {
        return sseReturn(entityFlux,entityClass);
    }

    protected <T, P extends Publisher<T>> Mono<ServerResponse> sSEReponseBuild(P publisher, Class<T> elementClass) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .header("X-Accel-Buffering", "no")
                .body(publisher, elementClass);
    }

    protected <T, P extends Publisher<T>> Mono<ServerResponse> sSEReponseBuild(T o, Class<T> elementClass) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .header("X-Accel-Buffering", "no")
                .body(Mono.just(o), elementClass);
    }

    /**
     * 分页条件封装
     *
     * @param pageQuery
     * @return
     */
    protected Pageable getPageable(PageQuery pageQuery) {
        //排序条件
        if (ArrayUtil.isEmpty(pageQuery.getOrder())) {
            String[] order = {"_id"};
            pageQuery.setOrder(order);
        }
        Sort sort = new Sort(Sort.Direction.DESC, pageQuery.getOrder());
        //分页
        return PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), sort);
    }

    /**
     * 分页查询条件封装
     *
     * @param pageQuery
     * @return
     */
    private Query getQuery(PageQuery pageQuery) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 分页条件判断
     *
     * @param pageQuery
     */
    protected void checkPage(PageQuery pageQuery) {
        CheckUtil.checkEmpty("页数", pageQuery.getPage());
        CheckUtil.checkEmpty("条数", pageQuery.getSize());
    }

    /**
     * 设置id
     *
     * @param entity
     */
    private void setId(Object entity) {
        try {
            Field fieldId = entity.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(entity, fieldId);
        } catch (IllegalAccessException e) {
            throw new FieldNameException("id", "未知");
        } catch (NoSuchFieldException e) {
            throw new FieldNameException("id", "未知");
        }
    }

    /**
     * 获取id
     *
     * @param entity
     * @return
     */
    private Object getId(Object entity) {
        try {
            Field fieldId = entity.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);
            return fieldId.get(entity);
        } catch (IllegalAccessException e) {
            throw new FieldNameException("id", "未知");
        } catch (NoSuchFieldException e) {
            throw new FieldNameException("id", "未知");
        }
    }

    /**
     * 获取当前Entity对象
     *
     * @return
     */
    private Entity instance() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
