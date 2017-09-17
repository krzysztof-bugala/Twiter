package com.twiter.service.message;

import com.twiter.entity.message.Message;

import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
public interface MessageService {

    public Message getMessage(long id);

    public void update(Message message);

    public void persist(Message message);

    public List<Message> findMessagesByUserId(long userId);

    public Message createMessage(String content);

    public Message postMessage(long userId, String content);

    public List<Message> findMessagesByFollowedUsers(long userId);


}
