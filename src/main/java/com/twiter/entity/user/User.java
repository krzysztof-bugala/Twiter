package com.twiter.entity.user;

import com.twiter.entity.message.Message;

import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
public interface User {

    public long getId();

    public void setId(long id);

    public String getLogin();

    public void setLogin(String login);

    public String getFirstname();

    public void setFirstname(String firstname);

    public String getSurname();

    public void setSurname(String surname);

    public String getEmail();

    public void setEmail(String email);

    public List<Message> getMessages();

    public void setMessages(List<Message> messages);

    public List<User> getFollowedUsers();

    public void setFollowedUsers(List<User> followedUsers);

    public int hashCode();

    public boolean equals(Object object);

}
