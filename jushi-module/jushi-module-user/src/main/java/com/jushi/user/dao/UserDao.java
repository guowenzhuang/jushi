package com.jushi.user.dao;

import com.jushi.user.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserDao extends JpaRepository<SysUser,String>,JpaSpecificationExecutor<SysUser>{

    /**
     * 根据用户名查询用户
     */
    List<SysUser> findByUsername(@Param("username") String username);

}
