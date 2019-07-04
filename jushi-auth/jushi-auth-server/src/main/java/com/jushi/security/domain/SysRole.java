package com.jushi.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "sys_role")
public class SysRole extends AbstractAuditingEntity{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    private String value;

    @JsonIgnore
    @DBRef
    private Set<SysAuthority> authorities = new HashSet<>();


}