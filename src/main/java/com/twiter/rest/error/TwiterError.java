package com.twiter.rest.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kb on 2017-09-14.
 */
public class TwiterError implements Error {


    private List<String> errorMessages = new ArrayList<>();

    public TwiterError(String errorMessage){
        errorMessages.add(errorMessage);
    }

    public TwiterError(List<String> errorMessages){
        this.errorMessages.addAll(errorMessages);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

}
