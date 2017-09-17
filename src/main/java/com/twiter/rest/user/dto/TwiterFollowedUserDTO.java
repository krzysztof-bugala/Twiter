package com.twiter.rest.user.dto;

/**
 * Created by kb on 2017-09-14.
 */
public class TwiterFollowedUserDTO implements FollowedUserDTO {

    private long followedUserId;

    @Override
    public long getFollowedUserId() {
        return followedUserId;
    }

    @Override
    public void setFollowedUserId(long followedUserId) {
        this.followedUserId = followedUserId;
    }
}
