package com.twiter.service.message;

import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.entity.user.User;
import com.twiter.repository.message.MessageRepository;
import com.twiter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@Primary
@Service
public class TwiterMessageService implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public Message getMessage(long id) {
        return messageRepository.getMessage(id);
    }

    @Override
    public void update(Message message) {
        messageRepository.update(message);
    }

    @Override
    public void persist(Message message) {
        messageRepository.persist(message);
    }

    @Override
    public List<Message> findMessagesByUserId(long userId) {
        userService.getUser(userId);
        return messageRepository.findMessagesByUserId(userId);
    }

    @Override
    public Message createMessage(String content) {
        Message message = new TwiterMessage();
        message.setContent(content);
        Date creationDate = new Date();
        message.setCreationDate(creationDate);
        messageRepository.persist(message);
        return message;
    }

    @Override
    @Transactional
    public Message postMessage(long userId, String content) {
        Message message = createMessage(content);
        User messageOwner = userService.createDefaultUserIfUserNotExist(userId);

        message.setOwner(messageOwner);
        messageRepository.persist(message);

        return message;
    }

    public List<Message> findMessagesByFollowedUsers(long userId) {
        userService.getUser(userId);
        return messageRepository.findMessagesByFollowedUsers(userId);
    }
}
