package com.jushi.user.dao;

import com.jushi.user.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDao extends JpaRepository<SysUser,String>,JpaSpecificationExecutor<SysUser>{

    /**
     * 根据用户名查询用户
     */
    @Query("select count(u.id) from SysUser u where u.username=:username")
    Long isUserByName(@Param("username") String username);

}
