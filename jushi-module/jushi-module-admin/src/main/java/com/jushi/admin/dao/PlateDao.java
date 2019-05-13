package com.jushi.admin.dao;

import com.jushi.admin.pojo.po.Plate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlateDao extends JpaRepository<Plate,Long>, JpaSpecificationExecutor<Plate> {

    /**
     * 查询前十个板块
     * @param pageable
     * @return
     */
    @Query(value = "select p.id , p.name from Plate p")
    List<Plate> findByPageable(Pageable pageable);
}
