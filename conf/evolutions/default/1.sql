# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contact (
  contact_id                    bigint auto_increment not null,
  contact_name                  varchar(255) not null,
  contact_number                varchar(255) not null,
  user_user_id                  bigint,
  constraint pk_contact primary key (contact_id)
);

create table user (
  user_id                       bigint auto_increment not null,
  first_name                    varchar(255) not null,
  last_name                     varchar(255) not null,
  email                         varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_user primary key (user_id)
);

alter table contact add constraint fk_contact_user_user_id foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_contact_user_user_id on contact (user_user_id);


# --- !Downs

alter table contact drop foreign key fk_contact_user_user_id;
drop index ix_contact_user_user_id on contact;

drop table if exists contact;

drop table if exists user;

