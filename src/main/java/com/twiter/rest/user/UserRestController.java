package com.twiter.rest.user;

import com.twiter.entity.message.Message;
import com.twiter.entity.user.User;
import com.twiter.rest.user.dto.TwiterFollowedUserDTO;
import com.twiter.rest.user.dto.TwiterPostedMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
public interface UserRestController {

    public ResponseEntity<User> getUser(long userId);

    public ResponseEntity<?> postMessage(long userId, TwiterPostedMessageDTO postedMessage);

    public ResponseEntity<?> getFollowedUsers(long userId);

    public ResponseEntity<List<Message>> getUserMessages(long userId);

    public ResponseEntity<?>  addFollowedUser(long userId, TwiterFollowedUserDTO twitterFollowedUser);

    public ResponseEntity<?>  deleteFollowedUser(long userId, TwiterFollowedUserDTO twitterFollowedUser);

    public ResponseEntity<?> timeline(long userId);
}
