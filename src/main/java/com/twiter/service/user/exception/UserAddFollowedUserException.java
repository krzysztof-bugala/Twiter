package com.twiter.service.user.exception;

import com.twiter.exception.TwiterException;
import com.twiter.rest.error.Error;
import org.springframework.http.HttpStatus;

/**
 * Created by kb on 2017-09-14.
 */
public class UserAddFollowedUserException extends TwiterException {

    public UserAddFollowedUserException() {
        this.errorMessage = Error.FOLLOWED_USER_CANT_BE_ADDED;
        this.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
