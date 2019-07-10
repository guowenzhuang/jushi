package com.jushi.security.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "sys_authority")
public  class SysAuthority extends AbstractAuditingEntity{
    @Id
    private String id;
    private String name;
    private String value;


}