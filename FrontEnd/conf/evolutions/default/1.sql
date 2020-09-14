# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article_titles (
  id                            varchar(255) not null,
  titles                        varchar(255),
  constraint pk_article_titles primary key (id)
);

create table author_articles (
  id                            varchar(255) not null,
  author                        varchar(255),
  year                          varchar(255),
  constraint pk_author_articles primary key (id)
);

create table author_publications (
  id                            varchar(255) not null,
  author                        varchar(255),
  year                          varchar(255),
  constraint pk_author_publications primary key (id)
);

create table authors (
  id                            varchar(255) not null,
  author                        varchar(255),
  constraint pk_authors primary key (id)
);

create table co_authors (
  id                            varchar(255) not null,
  constraint pk_co_authors primary key (id)
);

create table conference_locations (
  id                            varchar(255) not null,
  conference_name               varchar(255),
  year_start                    varchar(255),
  year_end                      varchar(255),
  constraint pk_conference_locations primary key (id)
);

create table journal_meta_data (
  id                            varchar(255) not null,
  journal_name                  varchar(255),
  volume                        integer not null,
  number                        integer not null,
  paper_title                   varchar(255),
  authors                       varchar(255),
  publication_channel           varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  constraint pk_journal_meta_data primary key (id)
);

create table paper_meta_data (
  id                            varchar(255) not null,
  paper_name                    varchar(255),
  paper_title                   varchar(255),
  authors                       varchar(255),
  publication_channel           varchar(255),
  time                          varchar(255),
  pages                         varchar(255),
  constraint pk_paper_meta_data primary key (id)
);

create table popular_authors (
  id                            varchar(255) not null,
  constraint pk_popular_authors primary key (id)
);

create table result (
  paper_title                   varchar(255),
  year                          integer not null,
  publication_channel           varchar(255),
  pages                         varchar(255),
  id                            varchar(255)
);


# --- !Downs

drop table if exists article_titles;

drop table if exists author_articles;

drop table if exists author_publications;

drop table if exists authors;

drop table if exists co_authors;

drop table if exists conference_locations;

drop table if exists journal_meta_data;

drop table if exists paper_meta_data;

drop table if exists popular_authors;

drop table if exists result;

