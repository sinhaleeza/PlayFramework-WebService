# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book_chapter_publication (
  book_name                     varchar(255),
  editors_name                  varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  paper_id                      varchar(255)
);

create table book_publication (
  book_name                     varchar(255),
  publisher_name                varchar(255),
  publisher_location            varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  paper_id                      varchar(255)
);

create table conference (
  time                          varchar(255),
  conference_name               varchar(255),
  book_title                    varchar(255),
  publisher                     varchar(255),
  year                          integer not null,
  isbn                          varchar(255),
  id                            varchar(255)
);

create table conference_publication (
  conference_name               varchar(255),
  location                      varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  crossref                      varchar(255),
  book_title                    varchar(255),
  paper_id                      varchar(255)
);

create table journal_publication (
  journal_name                  varchar(255),
  volume                        integer not null,
  number                        integer not null,
  time                          varchar(255),
  pages                         varchar(255),
  paper_id                      varchar(255)
);

create table publication (
  paper_title                   varchar(255),
  publication_channel           varchar(255),
  year                          integer not null,
  book_title                    varchar(255),
  id                            varchar(255),
  journal_id                    varchar(255),
  conference_id                 varchar(255),
  book_id                       varchar(255),
  book_chapter_id               varchar(255)
);

create table result (
  paper_title                   varchar(255),
  year                          integer not null,
  publication_channel           varchar(255),
  pages                         varchar(255),
  id                            varchar(255)
);

create table result2 (
  id                            varchar(255) not null,
  paper_name                    varchar(255),
  paper_title                   varchar(255),
  authors                       varchar(255),
  publication_channel           varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  constraint pk_result2 primary key (id)
);

create table result3 (
  id                            varchar(255) not null,
  journal_name                  varchar(255),
  volume                        integer not null,
  number                        integer not null,
  paper_title                   varchar(255),
  authors                       varchar(255),
  publication_channel           varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  constraint pk_result3 primary key (id)
);

create table result4 (
  id                            varchar(255) not null,
  constraint pk_result4 primary key (id)
);

create table result5 (
  id                            varchar(255) not null,
  titles                        varchar(255),
  constraint pk_result5 primary key (id)
);

create table result6 (
  id                            varchar(255) not null,
  constraint pk_result6 primary key (id)
);

create table result7 (
  id                            varchar(255) not null,
  constraint pk_result7 primary key (id)
);

create table result9 (
  id                            varchar(255) not null,
  constraint pk_result9 primary key (id)
);


# --- !Downs

drop table if exists book_chapter_publication;

drop table if exists book_publication;

drop table if exists conference;

drop table if exists conference_publication;

drop table if exists journal_publication;

drop table if exists publication;

drop table if exists result;

drop table if exists result2;

drop table if exists result3;

drop table if exists result4;

drop table if exists result5;

drop table if exists result6;

drop table if exists result7;

drop table if exists result9;

