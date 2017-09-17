package com.twiter.service.message;

import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.user.UserRepository;
import com.twiter.repository.user.exception.UserNotFoundException;
import com.twiter.rest.user.dto.FollowedUserDTO;
import com.twiter.rest.user.dto.TwiterFollowedUserDTO;
import com.twiter.service.sequence.SequenceService;
import com.twiter.service.user.TwiterUserService;
import com.twiter.service.user.UserService;
import com.twiter.service.user.exception.FollowedUserNotFoundException;
import com.twiter.service.user.exception.UserAddFollowedUserException;
import com.twiter.service.user.exception.UserDeleteUnfollowedUserException;
import com.twiter.service.user.exception.UserFollowHimselfException;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TwiterUserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private SequenceService sequenceService;

    @TestConfiguration
    static class TwitterUserServiceTestContextConfiguration {

        @Bean
        public UserService testUserService() {
            return new TwiterUserService();
        }
    }

    @MockBean
    private UserRepository userRepository;

    @Mock
    private User user;

    @Test
    public void whenGetUser_thenUserRepositoryGetUserIsCalled() {
        //given
        long id = 7;

        //when
        Mockito.when(userRepository.getUser(id)).thenReturn(user);
        User foundUser = userService.getUser(id);


        //then
        Assert.assertEquals(user, foundUser);
        Mockito.verify(userRepository, Mockito.times(1)).getUser(id);
    }

    @Test
    public void whenGetUserByLogin_thenUserReposityGetUserByLoginIsCalled() {
        //given
        String login = "login";

        //when
        Mockito.when(userRepository.getUserByLogin(login)).thenReturn(user);
        User foundUser = userService.getUserByLogin(login);

        //then
        Assert.assertEquals(user, foundUser);
        Mockito.verify(userRepository, Mockito.times(1)).getUserByLogin(login);
    }

    @Test
    public void whenUpdate_thenUserRepositoryUpdateIsCalled() {
        //when
        userRepository.update(user);

        //then
        Mockito.verify(userRepository, Mockito.times(1)).update(user);
    }

    @Test
    public void whenPersist_thenUserRepositoryPersistIsCalled() {
        //when
        userRepository.persist(user);

        //then
        Mockito.verify(userRepository, Mockito.times(1)).persist(user);
    }

    @Test
    public void whenCreateDefaultUser_thenReturnDefualtUser() {
        //given
        Mockito.when(sequenceService.getSequenceNextValue("default_user_sequence")).thenReturn(5L);

        //when
        User defaultUser = userService.createDefaultUser();

        //then
        Assert.assertEquals(defaultUser.getFirstname(), "firstname5");
        Assert.assertEquals(defaultUser.getSurname(), "surname5");
        Assert.assertEquals(defaultUser.getEmail(), "5@test.com");
    }

    @Test
    public void whenFindFollowedUsers_thenReturnFollowedUsers() {
        //given
        long userId = 5;
        List<User> folllowedUsers = new ArrayList<>();
        User user = new TwiterUser();
        user.setFollowedUsers(folllowedUsers);
        Mockito.when(userRepository.getUser(userId)).thenReturn(user);

        //when
        List<User> returnedFolllowedUsers = userService.findFollowedUsers(userId);

        //then
        Assert.assertEquals(returnedFolllowedUsers, folllowedUsers);

    }

    @Test
    public void whenFindFollowedUser_thenReturnFollowedUsr() {
        //given
        long userId = 5;
        List<User> folllowedUsers = new ArrayList<>();
        User followedUser = new TwiterUser();
        followedUser.setId(userId);
        followedUser.setFollowedUsers(folllowedUsers);
        Mockito.when(userRepository.getUser(userId)).thenReturn(followedUser);

        //when
        User returnedFollowedUser = userService.findFollowedUser(userId);

        //then
        Assert.assertEquals(returnedFollowedUser, followedUser);

    }

    @Test(expected = FollowedUserNotFoundException.class)
    public void whenFindFollowedUser_thenThrowFollowedUserNotFoundException() {
        //given
        long userId = 5;
        List<User> folllowedUsers = new ArrayList<>();
        User followedUser = new TwiterUser();
        followedUser.setId(userId);
        followedUser.setFollowedUsers(folllowedUsers);
        Mockito.when(userRepository.getUser(userId)).thenThrow(UserNotFoundException.class);

        //when
        User returnedFollowedUser = userService.findFollowedUser(userId);
    }

    @Test
    public void whenAdFollowedUser_thenReturnAddedFollowedUser() {
        //given
        long userId = 7;
        long followedUserId = 44;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User user = new TwiterUser();
        List<User> followedUsers = new ArrayList<>();
        user.setFollowedUsers(followedUsers);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        User addedFollowedUser = spiedUserService.addFollowedUser(userId, followedUserDTO);

        //then
        Assert.assertEquals(followedUser, addedFollowedUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenAdFollowedUser_thenThrowUserNotFoundException() {
        //given
        long userId = 7;
        long followedUserId = 44;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);
        Mockito.when(spiedUserService.getUser(userId)).thenThrow(UserNotFoundException.class);

        User user = new TwiterUser();
        List<User> followedUsers = new ArrayList<>();
        user.setFollowedUsers(followedUsers);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.addFollowedUser(userId, followedUserDTO);
    }

    @Test(expected = UserFollowHimselfException.class)
    public void whenAdFollowedUser_thenThrowUserFollowHimselfException() {
        //given
        long userId = 7;
        long followedUserId = 7;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);


        User user = new TwiterUser();
        List<User> followedUsers = new ArrayList<>();
        user.setFollowedUsers(followedUsers);
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.addFollowedUser(userId, followedUserDTO);
    }

    @Test(expected = FollowedUserNotFoundException.class)
    public void whenAdFollowedUser_thenThrowFollowedUserNotFoundException() {
        //given
        long userId = 7;
        long followedUserId = 7;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);


        User user = new TwiterUser();
        List<User> followedUsers = new ArrayList<>();
        user.setFollowedUsers(followedUsers);
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenThrow(FollowedUserNotFoundException.class);

        //when
        spiedUserService.addFollowedUser(userId, followedUserDTO);
    }

    @Test(expected = UserAddFollowedUserException.class)
    public void whenAdFollowedUser_thenThrowUserAddFollowedUserException() {
        //given
        long userId = 7;
        long followedUserId = 8;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);

        User user = new TwiterUser();
        List<User> followedUsers = new ArrayList<>();
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);
        followedUsers.add(followedUser);
        user.setFollowedUsers(followedUsers);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.addFollowedUser(userId, followedUserDTO);
    }

    @Test
    public void whenDeleteFollowedUser_thenRemoveUserFromFollowedUserList() {
        //given
        long userId = 7;
        long followedUserId = 8;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);

        User user = new TwiterUser();
        List<User> followedUsers = Mockito.spy(new ArrayList<>());
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);
        followedUsers.add(followedUser);
        user.setFollowedUsers(followedUsers);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.deleteFollowedUser(userId, followedUserDTO);

        //then
        Mockito.verify(followedUsers, Mockito.times(1)).remove(followedUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenDeleteFollowedUser_thenThrowUserNotFoundException() {
        //given
        long userId = 7;
        long followedUserId = 8;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);

        User user = new TwiterUser();
        List<User> followedUsers = Mockito.spy(new ArrayList<>());
        Mockito.when(spiedUserService.getUser(userId)).thenThrow(UserNotFoundException.class);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);
        followedUsers.add(followedUser);
        user.setFollowedUsers(followedUsers);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.deleteFollowedUser(userId, followedUserDTO);
    }

    @Test(expected = FollowedUserNotFoundException.class)
    public void whenDeleteFollowedUser_thenFollowedUserNotFoundExcepiton() {
        //given
        long userId = 7;
        long followedUserId = 8;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);

        User user = new TwiterUser();
        List<User> followedUsers = Mockito.spy(new ArrayList<>());
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);
        followedUsers.add(followedUser);
        user.setFollowedUsers(followedUsers);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenThrow(FollowedUserNotFoundException.class);

        //when
        spiedUserService.deleteFollowedUser(userId, followedUserDTO);
    }

    @Test(expected = UserDeleteUnfollowedUserException.class)
    public void whenDeleteFollowedUser_thenThrowUserDeleteUnfollowedUserException() {
        //given
        long userId = 7;
        long followedUserId = 8;
        FollowedUserDTO followedUserDTO = new TwiterFollowedUserDTO();
        followedUserDTO.setFollowedUserId(followedUserId);

        UserService spiedUserService = Mockito.spy(userService);

        User user = new TwiterUser();
        List<User> followedUsers = Mockito.spy(new ArrayList<>());
        Mockito.when(spiedUserService.getUser(userId)).thenReturn(user);

        User followedUser = new TwiterUser();
        followedUser.setId(followedUserId);
        user.setFollowedUsers(followedUsers);

        Mockito.when(spiedUserService.findFollowedUser(followedUserId)).thenReturn(followedUser);

        //when
        spiedUserService.deleteFollowedUser(userId, followedUserDTO);
    }

}
