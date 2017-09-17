package com.twiter.rest.error.factory;

import com.twiter.rest.error.Error;
import com.twiter.rest.error.TwiterError;
import org.springframework.stereotype.Component;

/**
 * Created by kb on 2017-09-15.
 */
@Component
public class TwiterErrorFactory implements ErrorFactory {
    public Error getInstance(String errorMessage) {
        return new TwiterError(errorMessage);
    }
}
