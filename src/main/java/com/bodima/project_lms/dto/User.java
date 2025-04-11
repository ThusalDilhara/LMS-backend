package com.bodima.project_lms.dto;

import com.bodima.project_lms.model.AuthorityEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;


@Data
public class User {
    @Id
    private Integer id;

    private String name;

    private String email;

    private String mobileNumber;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private Set<AuthorityEntity> authorities;

}
