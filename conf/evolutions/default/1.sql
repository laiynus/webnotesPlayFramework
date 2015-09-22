# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table notes (
  id                        integer not null,
  note                      varchar(255) not null,
  dateTimeCreate            timestamp not null,
  username                  varchar(255),
  constraint pk_notes primary key (id))
;

create table users (
  username                  varchar(255) not null,
  password                  varchar(255) not null,
  constraint pk_users primary key (username))
;

create sequence notes_seq;

create sequence users_seq;

alter table notes add constraint fk_notes_user_1 foreign key (username) references users (username);
create index ix_notes_user_1 on notes (username);



# --- !Downs

drop table if exists notes cascade;

drop table if exists users cascade;

drop sequence if exists notes_seq;

drop sequence if exists users_seq;

