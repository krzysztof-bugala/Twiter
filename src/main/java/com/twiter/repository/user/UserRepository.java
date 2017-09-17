package com.twiter.repository.user;

import com.twiter.entity.user.User;

/**
 * Created by kb on 2017-09-12.
 */
public interface UserRepository {

    public User getUser(long id);

    public User getUserByLogin(String login);

    public void update(User user);

    public void persist(User user);

    public long getDefaultUserSequenceNextValue();
}
