package com.twiter.rest.error.factory;

import com.twiter.rest.error.Error;

/**
 * Created by kb on 2017-09-15.
 */
public interface ErrorFactory {
    public Error getInstance(String errorMessage);
}
