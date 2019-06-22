package com.jushi.auth.server.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "sys_authority")
public  class SysAuthority extends AbstractAuditingEntity{
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String value;


}