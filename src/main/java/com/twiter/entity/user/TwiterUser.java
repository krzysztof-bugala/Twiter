package com.twiter.entity.user;

import com.fasterxml.jackson.annotation.*;
import com.twiter.entity.message.Message;
import com.twiter.entity.message.TwiterMessage;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@Entity
@Table(name = "user")
public class TwiterUser implements User {

    @Id
    @SequenceGenerator(name = "userGenerator", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(generator = "userGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name="user_id")
    private Long id;

    @Column(name="login")
    @JsonIgnore
    private String login;

    @Column(name="firstname")
    private String firstname;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    @JsonIgnore
    private String email;

    @OneToMany(
            targetEntity = TwiterMessage.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name="user_id")
    @JsonIgnore
    private List<Message> messages;

    @ManyToMany(targetEntity=TwiterUser.class)
    @JoinTable(
            name="user_followed_user",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")},
            inverseJoinColumns={@JoinColumn(name="followed_user_id", referencedColumnName="user_id")})
    @JsonIgnore
    private List<User> followedUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof User) {
            User user = (User)object;
            return id.equals(user.getId());
        }

        return false;
    }
}
