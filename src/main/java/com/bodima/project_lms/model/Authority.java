package com.bodima.project_lms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authorities")
@Data
public class Authority {

    @Id
    private String id;
    private String authorityName;
}
