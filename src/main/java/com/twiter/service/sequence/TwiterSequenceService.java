package com.twiter.service.sequence;

import com.twiter.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kb on 2017-09-15.
 */
@Service
public class TwiterSequenceService implements SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    public long getSequenceNextValue(String sequenceName) {
        return sequenceRepository.getSequenceNextValue(sequenceName);
    }


}
