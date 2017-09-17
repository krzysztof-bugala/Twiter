package com.twiter.repository.message;

import com.twiter.entity.message.Message;

import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
public interface MessageRepository {

    public Message getMessage(long id);

    public void update(Message message);

    public void persist(Message message);

    public List<Message> findMessagesByUserId(long userId);

    public List<Message> findMessagesByFollowedUsers(long userId);

}
