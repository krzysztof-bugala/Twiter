package com.twiter.entity.message;

import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kb on 2017-09-12.
 */
@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "findMessagesByUserId",
                query = "select message from TwiterMessage message join message.owner owner where owner.id = :userId order by " +
                        "message.creationDate desc, message.id desc"),
        @NamedQuery(name = "findMessagesByFollowedUsers",
                query = "select message from TwiterMessage message join message.owner owner where owner.id in " +
                        "(select followedUser.id from TwiterUser user join user.followedUsers followedUser where user.id = :userId) " +
                        "order by message.creationDate desc, message.id desc")
})
public class TwiterMessage implements Message {
    @Id
    @SequenceGenerator(name = "messageGenerator", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(generator = "messageGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name="message_id")
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="creation_date")
    private Date creationDate;

    @ManyToOne(targetEntity = TwiterUser.class)
    @JoinColumn(name="user_id")
    private User owner;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof Message) {
            Message message = (Message)object;
            return id.equals(message.getId());
        }

        return false;
    }
}
