package com.twiter.service.user.exception;

import com.twiter.exception.TwiterException;
import com.twiter.rest.error.Error;
import org.springframework.http.HttpStatus;

/**
 * Created by kb on 2017-09-14.
 */
public class UserFollowHimselfException extends TwiterException {

    public UserFollowHimselfException() {
        this.errorMessage = Error.USER_FOLLOWS_HIMSELF;
        this.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
