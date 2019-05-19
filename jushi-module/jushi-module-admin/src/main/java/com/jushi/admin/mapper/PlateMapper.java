package com.jushi.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jushi.admin.pojo.po.PlatePO;
import org.apache.ibatis.annotations.Param;

public interface PlateMapper extends BaseMapper<PlatePO> {
    /**
     * 根据权重排序查询前十个板块
     * @param page
     * @return
     */
    IPage<PlatePO> findTop10(Page page);

}
