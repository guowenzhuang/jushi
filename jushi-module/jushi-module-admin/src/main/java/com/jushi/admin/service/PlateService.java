package com.jushi.admin.service;

import com.jushi.admin.dao.PlateDao;
import com.jushi.admin.pojo.po.Plate;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateService {
    @Autowired
    private PlateDao plateDao;

    public Result findTop10(){
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable=PageRequest.of(0,10,sort);
        List<Plate> plates = plateDao.findByPageable(pageable);
        Result<List<Plate>> result=new Result<List<Plate>>(true, StatusCode.OK.value(),"查询板块成功",plates);;
        return result;
    }
}
