package com.bodima.project_lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Serquence {
    @Id
    private String id;

    private int sequence;

}