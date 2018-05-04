# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                            serial not null,
  title                         varchar(255),
  price                         float,
  author                        varchar(255),
  constraint pk_book primary key (id)
);

create table users (
  username                      varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_users primary key (username)
);


# --- !Downs

drop table if exists book cascade;

drop table if exists users cascade;

