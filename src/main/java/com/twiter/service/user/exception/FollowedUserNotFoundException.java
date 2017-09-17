package com.twiter.service.user.exception;

import com.twiter.exception.TwiterException;
import com.twiter.rest.error.Error;
import org.springframework.http.HttpStatus;

/**
 * Created by kb on 2017-09-14.
 */
public class FollowedUserNotFoundException extends TwiterException {

    public FollowedUserNotFoundException() {
        this.errorMessage = Error.FOLLLOWED_USER_NOT_FOUND;
        this.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
