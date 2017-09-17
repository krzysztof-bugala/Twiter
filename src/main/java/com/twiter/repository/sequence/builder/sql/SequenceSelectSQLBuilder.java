package com.twiter.repository.sequence.builder.sql;

import org.springframework.stereotype.Component;

/**
 * Created by kb on 2017-09-15.
 */
@Component
public class SequenceSelectSQLBuilder implements SQLBuilder {

    private String sequenceName;

    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";
    private static final String DUAL = "DUAL";
    private static final String SPACE = " ";
    private static final String DOT = ".";
    private static final String NEXTVAL = "NEXTVAL";

    public SequenceSelectSQLBuilder() {
    }

    public SequenceSelectSQLBuilder(String sequenceName) {
        this.setSequenceName(sequenceName);
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String build() {
        StringBuilder sequenceSelect = new StringBuilder();
        sequenceSelect.append(SELECT);
        sequenceSelect.append(SPACE);
        sequenceSelect.append(sequenceName);
        sequenceSelect.append(DOT);
        sequenceSelect.append(NEXTVAL);
        sequenceSelect.append(SPACE);
        sequenceSelect.append(FROM);
        sequenceSelect.append(SPACE);
        sequenceSelect.append(DUAL);
        return sequenceSelect.toString();
    }

}
