package com.jushi.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jushi.admin.mapper.PlateMapper;
import com.jushi.admin.pojo.po.PlatePO;
import com.jushi.admin.service.PlateService;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PlateServiceImpl extends ServiceImpl<PlateMapper, PlatePO> implements PlateService {
    @Autowired(required = false)
    private PlateMapper plateMapper;

    public Result findTop10(){
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable= PageRequest.of(0,10,sort);
        Page<PlatePO> page=new Page<>(0,10);
        IPage<PlatePO> platePOPage = plateMapper.findTop10(page);
        Result<IPage<PlatePO>> result=new Result<IPage<PlatePO>>(true, StatusCode.OK.value(),"查询板块成功",platePOPage);
        return result;
    }
}
