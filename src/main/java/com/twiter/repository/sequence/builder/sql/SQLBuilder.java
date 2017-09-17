package com.twiter.repository.sequence.builder.sql;

/**
 * Created by kb on 2017-09-15.
 */
public interface SQLBuilder {
    public String getSequenceName();

    public void setSequenceName(String sequenceName);

    public String build();
}
