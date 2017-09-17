Twiter
======

 

Description
-----------

Twiter is a simple social networking application, similar to Twitter, which
exposes functionality through a web API. The application supports the scenarios
below.

 

Scenarios
---------

### Posting

A user is able to post a 140 character message.

### Wall

A user is able to see a list of the messages they've posted, in reverse
chronological order.

### Following

A user is able to follow another user. Following isn’t reciprocal.

### Timeline

A user is able to see a list of the messages posted by all the people they
follow, in reverse chronological order.

 

Technologies used in project
----------------------------

-   Spring boot

-   H2 memory database

-   Junit

-   Mockito

-   JPA

-   Java 8

 

Installation and running
------------------------

Enter to folder Twiter and run command following command.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
mvn spring-boot:run
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The above command will build and run application.

The web API may be tested on browser by using following address

### http://localhost:8080/api/v1/twiter/users/1/user

 

Default configuration
---------------------

There are four users which have userId for use in web API.

-   1

-   2

-   3

-   4

 

Twiter web API
--------------

 

### GET /api/v1/twiter/users/{userId}

Returns information about the user

Errors

-   User doesn't exists!

Example request  
GET /api/v1/twiter/users/1

 

### GET /api/v1/twiter/users/{userId}/messages

Returns messages posted by user {userId} in reverse chronological order. Wall
scenario.  
Errors

-   User doesn't exists!

-   Message can't be longer than 140 characters!

-   Message can’t be empty!

Example request  
GET /api/v1/twiter/users/1/messages

 

### POST /api/v1/twiter/users/{userId}/messages

The message is posted by user {userId}. If the user doesn’t exists ,the user is
created and the message is posted by him. Posting scenario.

Errors

-   Message can't be longer than 140 characters!

Example request  
POST /api/v1/twiter/users/1/messages

{

"content" : "The message to post"

}

 

### GET /api/v1/twiter/users/{userId}/followedUsers

Returned the users which are followed by user {userId}

Errors

-   User doesn't exists!

Example request  
GET /api/v1/twiter/users/1/followedUsers

 

### POST /api/v1/twiter/users/{userId}/followedUsers

The user {userId} adds the selected user {followedUserId} to the followed users
list. Follow scenario.

Errors

-   User doesn't exists!

-   Followed user doesn't exists!

-   User can't follow himself!

-   The selected user is already followed!

Example request

POST /api/v1/twiter/users/1/followedUsers

{

"followedUserId" : "1"

}

 

### DELETE /api/v1/twiter/users/{userId}/followedUsers

The user {userId} delete the selected user {followedUserId} from the followed
users list. Unfollowed.

Errors

-   User doesn't exists!

-   Followed user doesn't exists!

-   The unfollowed user can't be removed!

Example request

DELETE /api/v1/twiter/users/1/followedUsers

{

"followedUserId" : "1"

}

 

### GET /api/v1/twiter/users/{userId}/followedUsers/timeline

Returns the messages of the followed users by user {userId} in reverse
chronological order. Timeline scenario.

Errors

-   User doesn't exists!

Example request

GET /api/v1/twiter/users/1/followedUsers/timeline

 

 
