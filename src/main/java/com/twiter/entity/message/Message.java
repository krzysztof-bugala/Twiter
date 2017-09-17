package com.twiter.entity.message;

import com.twiter.entity.user.User;

import java.util.Date;

/**
 * Created by kb on 2017-09-12.
 */
public interface Message {
    public long getId();

    public void setId(long id);

    public String getContent();

    public void setContent(String content);

    public Date getCreationDate();

    public void setCreationDate(Date date);

    public User getOwner();

    public void setOwner(User owner);

    public int hashCode();

    public boolean equals(Object object);

}
