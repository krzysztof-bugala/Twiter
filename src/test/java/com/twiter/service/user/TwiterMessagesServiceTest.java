package com.twiter.service.user;

import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.message.MessageRepository;
import com.twiter.service.message.MessageService;
import com.twiter.service.message.TwiterMessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kb on 2017-09-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TwiterMessagesServiceTest {

    @Autowired
    private MessageService messageService;

    @TestConfiguration
    static class TwitterMessageServiceTestContextConfiguration {

        @Bean
        public MessageService testMessageService() {
            return new TwiterMessageService();
        }
    }

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserService userService;

    @Mock
    private Message message;

    @Test
    public void whenGetUser_thenUserRepositoryGetUserIsCalled() {
        //given
        long id = 7;

        //when
        Mockito.when(messageRepository.getMessage(id)).thenReturn(message);
        Message foundMessage = messageService.getMessage(id);


        //then
        Assert.assertEquals(message, foundMessage);
        Mockito.verify(messageRepository, Mockito.times(1)).getMessage(id);
    }

    @Test
    public void whenUpdate_thenServiceRepositoryUpdateIsCalled() {
        //when
        messageRepository.update(message);

        //then
        Mockito.verify(messageRepository, Mockito.times(1)).update(message);
    }

    @Test
    public void whenPersist_thenServiceRepositoryPersistIsCalled() {
        //when
        messageRepository.persist(message);

        //then
        Mockito.verify(messageRepository, Mockito.times(1)).persist(message);
    }

    @Test
    public void whenCreateMessage_thenReturnCreatedMessage() {
        //given
        String content = "test content";

        //when
        Message message = messageService.createMessage(content);

        //then
        Assert.assertEquals(message.getContent(), content);
    }

    @Test
    public void whenPostMessage_thenReturnMessageAndSetOwner() {
        //given
        long userId = 7;
        String content = "dfewrew";
        Message message = new TwiterMessage();
        message.setContent(content);
        message.setId(4);
        User owner = new TwiterUser();
        owner.setId(userId);
        message.setOwner(owner);
        MessageService spiedMessageService = Mockito.spy(messageService);
        Mockito.doReturn(message).when(spiedMessageService).createMessage(content);
        Mockito.when(userService.createDefaultUserIfUserNotExist(userId)).thenReturn(owner);

        //when
        Message postedMessage = spiedMessageService.postMessage(userId, content);

        //then
        Assert.assertEquals(postedMessage, message);
        Assert.assertEquals(postedMessage.getOwner(), owner);
    }
}
