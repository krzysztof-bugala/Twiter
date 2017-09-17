drop table if exists user;

drop sequence if exists user_sequence;

drop sequence if exists default_user_sequence;

create sequence user_sequence start with 1;

create sequence default_user_sequence start with 1;



create table user(user_id number default user_sequence.nextval not null primary key,
                    login varchar2  unique,
                    firstname varchar2   not null,
                    surname varchar2 not null,
                    email varchar2  unique);
insert into user(login, firstname, surname, email) values ('JohnSmith', 'John', 'Smith', 'john.smith@gmail.com');
insert into user(login, firstname, surname, email) values ('JackMax', 'Jack', 'Max', 'john.max@gmail.com');
insert into user(login, firstname, surname, email) values ('MarkSeat', 'Mark', 'Seat', 'mark.seat@gmail.com');
insert into user(login, firstname, surname, email) values ('BratMar', 'Brat', 'Mar', 'brat.mar@gmail.com');

drop table if exists message;

drop sequence if exists message_sequence;

create sequence message_sequence start with 1;

create table message(message_id number default message_sequence.nextval not null primary key, user_id number, content varchar2,
                    creation_date timestamp default CURRENT_TIMESTAMP);
alter table message add foreign key (user_id)  references user (user_id);
create index message_user_id_index on message(user_id);
insert into message(user_id, content, creation_date) values(1, 'John Smith: Messages 1', CURRENT_TIMESTAMP - 12);
insert into message(user_id, content, creation_date) values(1, 'John Smith: Messages 2', CURRENT_TIMESTAMP - 11);
insert into message(user_id, content, creation_date) values(1, 'John Smith: Messages 3', CURRENT_TIMESTAMP - 10);
insert into message(user_id, content, creation_date) values(2, 'Jack Max: Messages 1', CURRENT_TIMESTAMP - 9);
insert into message(user_id, content, creation_date) values(2, 'Jack Max: Messages 2', CURRENT_TIMESTAMP - 8);
insert into message(user_id, content, creation_date) values(2, 'Jack Max: Messages 3', CURRENT_TIMESTAMP - 7);
insert into message(user_id, content, creation_date) values(3, 'Mark Seat: Messages 1', CURRENT_TIMESTAMP - 6);
insert into message(user_id, content, creation_date) values(3, 'Mark Seat: Messages 2', CURRENT_TIMESTAMP - 5);
insert into message(user_id, content, creation_date) values(3, 'Mark Seat: Messages 3', CURRENT_TIMESTAMP - 4);
insert into message(user_id, content, creation_date) values(4, 'Brat Mar: Messages 1', CURRENT_TIMESTAMP - 3);
insert into message(user_id, content, creation_date) values(4, 'Brat Mar: Messages 2', CURRENT_TIMESTAMP - 2);
insert into message(user_id, content, creation_date) values(4, 'Brat Mar: Messages 3', CURRENT_TIMESTAMP -1);



drop table if exists user_followed_user;

create table user_followed_user(user_id number not null, followed_user_id number not null);

alter table user_followed_user add primary key (user_id, followed_user_id);
alter table user_followed_user add foreign key (user_id)  references user (user_id);
alter table user_followed_user add foreign key (followed_user_id)  references user (user_id);
create index user_followed_user_user_id_index on user_followed_user(user_id);
create index user_followed_user_followed_user_id_index on user_followed_user(followed_user_id);

insert into user_followed_user(user_id, followed_user_id) values(1, 2);
insert into user_followed_user(user_id, followed_user_id) values(1, 4);
insert into user_followed_user(user_id, followed_user_id) values(2, 1);
insert into user_followed_user(user_id, followed_user_id) values(2, 4);
insert into user_followed_user(user_id, followed_user_id) values(2, 3);
insert into user_followed_user(user_id, followed_user_id) values(3, 1);
insert into user_followed_user(user_id, followed_user_id) values(4, 3);

