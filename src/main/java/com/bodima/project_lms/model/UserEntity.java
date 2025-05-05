package com.bodima.project_lms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String verifyCode;
    private Boolean active;
    private Boolean verified;
    private String role;
    private Date createDateTime = new Date();
}