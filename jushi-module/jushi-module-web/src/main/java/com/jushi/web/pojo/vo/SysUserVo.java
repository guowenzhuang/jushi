package com.jushi.web.pojo.vo;

import com.jushi.api.pojo.po.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = -4292493510110770597L;
    @Id
    private String id;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String email;
    private String firstName;
    private String imageUrl;
    private String lastName;
    private String username;

    public void copyProperties(SysUserPO sysUserPO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(sysUserPO, this);
    }
}
