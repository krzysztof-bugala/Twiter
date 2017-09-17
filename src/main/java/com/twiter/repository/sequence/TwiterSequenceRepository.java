package com.twiter.repository.sequence;

import com.twiter.repository.sequence.builder.sql.SQLBuilder;
import com.twiter.repository.sequence.builder.sql.SequenceSelectSQLBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;

/**
 * Created by kb on 2017-09-15.
 */
@Repository
public class TwiterSequenceRepository implements SequenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getSequenceNextValue(String sequenceName) {
        SQLBuilder sequenceSelectBuilder = new SequenceSelectSQLBuilder(sequenceName);
        String sequenceSelect = sequenceSelectBuilder.build();
        Query q = entityManager.createNativeQuery(sequenceSelect);
        BigInteger sequenceValue = (BigInteger) q.getSingleResult();
        return sequenceValue.longValue();
    }
}
