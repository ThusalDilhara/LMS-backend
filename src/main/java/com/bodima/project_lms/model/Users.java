package com.bodima.project_lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "users")
@Data
public class Users {
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
    private Set<Authority> authorities;

}
