package com.twiter.repository.message;

import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.repository.message.exception.MessageNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@Repository
public class TwiterMessageRepository implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message getMessage(long id) {
        Message message = entityManager.find(TwiterMessage.class, id);
        if (message == null) {
            throw new MessageNotFoundException();
        }

        return message;
    }

    @Override
    public void update(Message message) {
        entityManager.merge(message);
    }

    @Override
    public void persist(Message message) {
        entityManager.persist(message);
    }

    @Override
    public List<Message> findMessagesByUserId(long userId) {
        TypedQuery<Message> query =
                entityManager.createNamedQuery("findMessagesByUserId", Message.class);
        query.setParameter("userId", userId);
        List<Message> messages = query.getResultList();
        return messages;
    }

    @Override
    public List<Message> findMessagesByFollowedUsers(long userId) {
        TypedQuery<Message> query =
                entityManager.createNamedQuery("findMessagesByFollowedUsers", Message.class);
        query.setParameter("userId", userId);
        List<Message> messages = query.getResultList();
        return messages;
    }
}
