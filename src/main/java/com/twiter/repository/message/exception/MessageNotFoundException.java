package com.twiter.repository.message.exception;

import com.twiter.exception.TwiterException;
import com.twiter.rest.error.Error;
import org.springframework.http.HttpStatus;


/**
 * Created by kb on 2017-09-12.
 */
public class MessageNotFoundException extends TwiterException {

    public MessageNotFoundException() {
        this.errorMessage = Error.MESSAGE_NOT_FOUND_EXCEPTION;
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
