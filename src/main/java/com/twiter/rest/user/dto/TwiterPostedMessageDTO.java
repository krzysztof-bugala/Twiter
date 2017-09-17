package com.twiter.rest.user.dto;

import javax.validation.constraints.Size;
import com.twiter.rest.error.Error;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by kb on 2017-09-13.
 */
public class TwiterPostedMessageDTO implements PostedMessageDTO {

    @NotEmpty(message = Error.MESSAGE_CANT_BE_EMPTY)
    @Size(max = 140, message = Error.MESSAGE_IS_TOO_LONG)
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
