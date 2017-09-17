package com.twiter.service.user;

import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.user.UserRepository;
import com.twiter.repository.user.exception.UserNotFoundException;
import com.twiter.rest.user.dto.FollowedUserDTO;
import com.twiter.service.sequence.SequenceService;
import com.twiter.service.user.exception.FollowedUserNotFoundException;
import com.twiter.service.user.exception.UserAddFollowedUserException;
import com.twiter.service.user.exception.UserDeleteUnfollowedUserException;
import com.twiter.service.user.exception.UserFollowHimselfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@Primary
@Service
public class TwiterUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public User getUser(long id)  {
        return userRepository.getUser(id);
    }

    @Override
    public User getUserByLogin(String login)  {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void persist(User user) {
        userRepository.persist(user);
    }

    @Transactional
    @Override
    public User createDefaultUser() {
        final String FIRSTNAME_DEFAULT = "firstname";
        final String SURNAME_DEFAULT = "surname";
        final String EMAIL_DOMAIN_DEFAULT = "@test.com";
        final String DEFAULT_USER_SEQUENCE = "default_user_sequence";
        Long defaultUserSequenceNextValue = sequenceService.getSequenceNextValue(DEFAULT_USER_SEQUENCE);
        String firstname = FIRSTNAME_DEFAULT + defaultUserSequenceNextValue;
        String surname = SURNAME_DEFAULT + defaultUserSequenceNextValue;
        String email = defaultUserSequenceNextValue + EMAIL_DOMAIN_DEFAULT;
        User user = new TwiterUser();
        user.setLogin(defaultUserSequenceNextValue.toString());
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);
        userRepository.persist(user);
        return user;
    }

    @Override
    public List<User> findFollowedUsers(long userId) {
        User user = userRepository.getUser(userId);
        return user.getFollowedUsers();
    }

    @Override
    public User findFollowedUser(long userId) {
        User followedUser = null;
        try {
            followedUser = getUser(userId);
        } catch (UserNotFoundException e) {
            throw new FollowedUserNotFoundException();
        }

        return followedUser;
    }

    @Transactional
    @Override
    public User addFollowedUser(long userId, FollowedUserDTO followedUserDTO) {
        User user = getUser(userId);
        long followedUserId = followedUserDTO.getFollowedUserId();

        if (userId == followedUserId) {
            throw new UserFollowHimselfException();
        }

        User followedUser = findFollowedUser(followedUserId);
        List<User> followedUsers = user.getFollowedUsers();

        if (followedUsers.contains(followedUser)) {
            throw new UserAddFollowedUserException();
        }

        user.getFollowedUsers().add(followedUser);
        userRepository.persist(user);
        return followedUser;
    }

    @Transactional
    @Override
    public void deleteFollowedUser(long userId, FollowedUserDTO followedUserDTO) {
        User user = getUser(userId);
        long followedUserId = followedUserDTO.getFollowedUserId();

        User followedUser = findFollowedUser(followedUserId);
        List<User> followedUsers = user.getFollowedUsers();

        if (!followedUsers.contains(followedUser)) {
            throw new UserDeleteUnfollowedUserException();
        }

        followedUsers.remove(followedUser);
        userRepository.persist(user);
    }

    @Override
    public User createDefaultUserIfUserNotExist(long userId) {
        User user;
        try {
            user = getUser(userId);
        } catch (UserNotFoundException e) {
            user = createDefaultUser();

        }

        return user;
    }


}
