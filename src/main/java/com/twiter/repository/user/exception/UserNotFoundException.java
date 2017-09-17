package com.twiter.repository.user.exception;

import com.twiter.exception.TwiterException;
import com.twiter.rest.error.Error;
import org.springframework.http.HttpStatus;

/**
 * Created by kb on 2017-09-12.
 */
public class UserNotFoundException extends TwiterException {

    public UserNotFoundException() {
        this.errorMessage = Error.USER_NOT_FOUND;
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
