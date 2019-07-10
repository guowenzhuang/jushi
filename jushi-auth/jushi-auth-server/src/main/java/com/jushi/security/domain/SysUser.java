package com.jushi.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "sys_user")
public class SysUser extends AbstractAuditingEntity{
    @Id
    private String id;

    private String username;

    @JsonIgnore
    private String password;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String email;


    @Field("image_url")
    private String imageUrl;


    @JsonIgnore
    @DBRef
    private Set<SysRole> roles = new HashSet<>();


    private Set<GrantedAuthority> authorities = new HashSet<>();


    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> userAuthotities = new ArrayList<>();
        for(SysRole role : this.roles){
            for(SysAuthority authority : role.getAuthorities()){
                userAuthotities.add(new SimpleGrantedAuthority(authority.getValue()));
            }
        }

        return userAuthotities;
    }


}