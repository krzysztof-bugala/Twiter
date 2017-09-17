package com.twiter.rest.user;

import com.twiter.entity.message.Message;
import com.twiter.entity.user.User;
import com.twiter.rest.error.factory.ErrorFactory;
import com.twiter.rest.user.dto.TwiterFollowedUserDTO;
import com.twiter.rest.user.dto.TwiterPostedMessageDTO;
import com.twiter.service.message.MessageService;
import com.twiter.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@RestController
@RequestMapping("api/v1/twiter/users")
public class TwiterUserRestController implements UserRestController {

    @Autowired
    @Qualifier("twiterMessageService")
    private MessageService messageService;

    @Autowired
    @Qualifier("twiterUserService")
    private UserService userService;

    @Autowired
    private ErrorFactory errorFactory;

    @Override
    @RequestMapping(value="/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable long userId) {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{userId}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable long userId) {
        List<Message> messages = messageService.findMessagesByUserId(userId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{userId}/messages", method = RequestMethod.POST)
    public ResponseEntity<?> postMessage(@PathVariable long userId, @Valid @RequestBody TwiterPostedMessageDTO postedMessage) {
        String messageContent = postedMessage.getContent();
        Message message =  messageService.postMessage(userId, messageContent);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{userId}/followedUsers", method = RequestMethod.GET)
    public ResponseEntity<?>  getFollowedUsers(@PathVariable long userId) {
        List<User> users = userService.findFollowedUsers(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @Override
    @RequestMapping(value="/{userId}/followedUsers", method = RequestMethod.POST)
    public ResponseEntity<?>  addFollowedUser(@PathVariable long userId, @RequestBody TwiterFollowedUserDTO twitterFollowedUser) {
        User followedUser= userService.addFollowedUser(userId, twitterFollowedUser);

        return new ResponseEntity<>(followedUser, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/{userId}/followedUsers", method = RequestMethod.DELETE)
    public ResponseEntity<?>  deleteFollowedUser(@PathVariable long userId, @RequestBody TwiterFollowedUserDTO twitterFollowedUser) {
        userService.deleteFollowedUser(userId, twitterFollowedUser);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    @RequestMapping(value="/{userId}/followedUsers/timeline", method = RequestMethod.GET)
    public ResponseEntity<?> timeline(@PathVariable long userId) {

        List<Message> messages = messageService.findMessagesByFollowedUsers(userId);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
