package com.shubh.jobportal.utitlity;

import java.security.SecureRandom;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.shubh.jobportal.entity.Sequence;
import com.shubh.jobportal.exception.JobPortalException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Utilities {

    private final MongoOperations mongoOperations;
    private static MongoOperations staticMongoOperations; // Static field

    @PostConstruct
    public void init() {
        staticMongoOperations = mongoOperations; // Assign instance to static field
    }

    public static Long getNextSequence(String key) throws JobPortalException {
        if (staticMongoOperations == null) {
            throw new IllegalStateException("MongoOperations is not initialized by Spring.");
        }

        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        Sequence seq = staticMongoOperations.findAndModify(query, update, options, Sequence.class);

        if (seq == null) {
            throw new JobPortalException("Unable to get sequence for key: " + key);
        }

        return seq.getSeq();
    }

    public static String generateOTP(){
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
