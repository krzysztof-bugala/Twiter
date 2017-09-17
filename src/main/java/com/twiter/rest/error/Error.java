package com.twiter.rest.error;

import java.util.List;

/**
 * Created by kb on 2017-09-14.
 */
public interface Error {

    public static final String MESSAGE_NOT_FOUND_EXCEPTION = "Messages doesn't exists";

    public static final String USER_NOT_FOUND = "User doesn't exists!";

    public static final String FOLLLOWED_USER_NOT_FOUND = "Followed user doesn't exists!";

    public static final String USER_FOLLOWS_HIMSELF = "User can't follow himself!";

    public static final String FOLLOWED_USER_CANT_BE_ADDED = "The selected user is already followed!";

    public static final String UNFOLLOWED_USER_CANT_BE_REMOVED = "The unfollowed user can't be removed!";

    public static final String MESSAGE_IS_TOO_LONG = "Message can't be longer than 140 characters!";

    public static final String MESSAGE_CANT_BE_EMPTY = "Message can't be empty";


    public List<String> getErrorMessages();
}
