package com.bodima.project_lms.service;


import com.bodima.project_lms.model.Serquence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    public int getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);

        Serquence counter = mongoOperations.findAndModify(
                query,
                update,
                options().returnNew(true).upsert(true),
                Serquence.class);

        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}