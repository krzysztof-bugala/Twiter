package com.twiter.repository.user;

import com.twiter.Twiter;
import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;
import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.user.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by kb on 2017-09-12.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes=Twiter.class)
public class TwiterUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenGetUser_thenReturnUser() {
        // given
        String login = "John.Smith";
        String firstname = "John";
        String surname = "Smith";
        String email = "john.smith@test.com";

        User user = new TwiterUser();
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        List<Message> messages = new ArrayList<>();
        String content = "Messages content 1";
        Date creationDate = new Date();
        Message message = new TwiterMessage();
        message.setContent(content);
        message.setCreationDate(creationDate);

        messages.add(message);

        user.setMessages(messages);


        String followedUserLogin = "Mark.Red";
        String followedUserFirstname = "Mark";
        String followedUserSurname = "Red";
        String followedUserEmail = "mark.red@test.com";

        List<User> followedUsers = new ArrayList<>();
        User followedUser = new TwiterUser();
        followedUser.setLogin(followedUserLogin);
        followedUser.setFirstname(followedUserFirstname);
        followedUser.setSurname(followedUserSurname);
        followedUser.setEmail(followedUserEmail);
        followedUsers.add(followedUser);
        user.setFollowedUsers(followedUsers);


        userRepository.persist(user);

        long userId = user.getId();

        // when
        User foundUser = userRepository.getUser(userId);

        // then
        assertEquals(foundUser.getId(), userId);
        assertEquals(foundUser.getLogin(), login);
        assertEquals(foundUser.getFirstname(), firstname);
        assertEquals(foundUser.getSurname(), surname);
        assertEquals(foundUser.getEmail(), email);

        message = foundUser.getMessages().stream().findFirst().get();;

        assertEquals(foundUser.getMessages().size(), 1);
        assertEquals(message.getContent(), content);
        assertEquals(message.getCreationDate(), creationDate);

        assertEquals(foundUser.getFollowedUsers().size(), 1);
        followedUser = foundUser.getFollowedUsers().get(0);
        assertEquals(followedUser.getLogin(), followedUserLogin);
        assertEquals(followedUser.getFirstname(), followedUserFirstname);
        assertEquals(followedUser.getSurname(), followedUserSurname);
        assertEquals(followedUser.getEmail(), followedUserEmail);

    }

    @Test(expected = UserNotFoundException.class)
    public void whenGetUserNotFound_thenThrowException() {
        // given
        String login = "John.Smith";
        String firstname = "John";
        String surname = "Smith";
        String email = "john.smith@test.com";

        User user = new TwiterUser();
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        userRepository.persist(user);

        // when
        User foundUser = userRepository.getUser(8);
    }

    @Test
    public void whenGetUserByLogin_thenReturnUser() {
        // given
        long id = 5;
        String login = "John.Smith";
        String firstname = "John";
        String surname = "Smith";
        String email = "john.smith@test.com";

        User user = new TwiterUser();
        //user.setId(id);
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        userRepository.persist(user);

        // when
        User foundUser = userRepository.getUserByLogin(login);

        // then
        assertEquals(foundUser.getLogin(), login);
        assertEquals(foundUser.getFirstname(), firstname);
        assertEquals(foundUser.getSurname(), surname);
        assertEquals(foundUser.getEmail(), email);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenGetUserByLoginNotFound_thenThrowException() {
        // given
        long id = 5;
        String login = "John.Smith";
        String firstname = "John";
        String surname = "Smith";
        String email = "john.smith@test.com";

        User user = new TwiterUser();
        //user.setId(id);
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        userRepository.persist(user);

        login = "test";

        // when
        User foundUser = userRepository.getUserByLogin(login);
    }

    @Test
    public void whenUpdate_thenReturnUpdatedUser() {
        // given
        long id = 5;
        String login = "John.Smith";
        String firstname = "John";
        String surname = "Smith";
        String email = "john.smith@test.com";

        User user = new TwiterUser();
        //user.setId(id);
        user.setLogin(login);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);

        userRepository.persist(user);

        surname = "Lenon";

        user.setSurname(surname);

        userRepository.update(user);

        // when
        User foundUser = userRepository.getUserByLogin(login);

        // then
        assertEquals(foundUser.getLogin(), login);
        assertEquals(foundUser.getFirstname(), firstname);
        assertEquals(foundUser.getSurname(), surname);
        assertEquals(foundUser.getEmail(), email);
    }
}
