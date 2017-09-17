package com.twiter.service.user;

import com.twiter.entity.user.User;
import com.twiter.rest.user.dto.FollowedUserDTO;

import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
public interface UserService {

    public User getUser(long id);

    public User getUserByLogin(String login);

    public void update(User user);

    public void persist(User user);

    public User createDefaultUser();

    public List<User> findFollowedUsers(long userId);

    public User findFollowedUser(long id);

    public User addFollowedUser(long userId, FollowedUserDTO followedUserDTO);

    public void deleteFollowedUser(long userId, FollowedUserDTO followedUserDTO);

    public User createDefaultUserIfUserNotExist(long userId);
}
