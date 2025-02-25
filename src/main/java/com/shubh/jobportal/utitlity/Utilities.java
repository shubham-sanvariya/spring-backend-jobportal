package com.shubh.jobportal.utitlity;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.shubh.jobportal.entity.Sequence;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Utilities {
    
    private final MongoOperations mongoOperations;

    public Long getNextSequence(String key) {
        Query query = new Query(Criteria.where("_id").is(key));

        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        Sequence seq = mongoOperations.findAndModify(query, update, options, Sequence.class);

        if (seq == null) {
            throw new IllegalStateException("Unable to get sequence for key: " + key);
        }

        return seq.getSeq();
    }
}
