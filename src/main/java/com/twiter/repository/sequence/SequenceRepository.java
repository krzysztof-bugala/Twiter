package com.twiter.repository.sequence;

/**
 * Created by kb on 2017-09-15.
 */
public interface SequenceRepository {

    public long getSequenceNextValue(String sequenceName);
}

