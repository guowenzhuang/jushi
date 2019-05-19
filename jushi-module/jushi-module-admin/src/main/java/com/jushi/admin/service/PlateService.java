package com.jushi.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jushi.admin.mapper.PlateMapper;
import com.jushi.admin.pojo.po.PlatePO;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlateService extends IService<PlatePO> {
    /**
     * 查询前十个板块 根据权重倒序
     * @return
     */
    Result findTop10();

}
