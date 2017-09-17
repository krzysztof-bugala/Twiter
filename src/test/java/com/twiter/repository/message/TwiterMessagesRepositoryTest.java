package com.twiter.repository.message;

import com.twiter.Twiter;
import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.message.exception.MessageNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by kb on 2017-09-12.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes=Twiter.class)
public class TwiterMessagesRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void whenGetMessage_thenReturnMessage() {
        // given
        String content = "First message content";
        Date creationDate = new Date();

        Message message = new TwiterMessage();
        message.setContent(content);
        message.setCreationDate(creationDate);

        String login = "Carl.Run";
        String firstname = "Carl";
        String surname = "Run";
        String email = "karl.run@test.com";


        User user = new TwiterUser();
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        message.setOwner(user);


        messageRepository.persist(message);

        long id = message.getId();

        // when
        Message foundMessage = messageRepository.getMessage(id);

        // then
        assertEquals(foundMessage.getId(), id);
        assertEquals(foundMessage.getContent(), content);
        assertEquals(foundMessage.getCreationDate(), creationDate);

        user = foundMessage.getOwner();
        assertEquals(user.getLogin(), login);
        assertEquals(user.getFirstname(), firstname);
        assertEquals(user.getSurname(), surname);
        assertEquals(user.getEmail(), email);
    }

    @Test(expected = MessageNotFoundException.class)
    public void whenGetMessageNotFound_thenThrowException() {
        // given
        String content = "Fisrt message content";
        Timestamp creationTimestamp =  new Timestamp(new Date().getTime());


        Message message = new TwiterMessage();
        message.setContent(content);
        message.setCreationDate(creationTimestamp);;

        messageRepository.persist(message);

        long id = 1000;

        // when
        Message foundMessage = messageRepository.getMessage(id);
    }
}
