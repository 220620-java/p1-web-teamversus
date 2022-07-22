
drop table if exists apikey;
drop table if exists inventory;
drop table if exists person;
drop table if exists album;
drop table if exists artist;

create table person (
    id serial primary key,
    username varchar(30) unique not null,
    passwrd varchar(30) not null,
    first_name varchar(30) not null,
    last_name varchar(30) not null
);

create table artist (
    id serial primary key,
    stage_name varchar(250) not null
);

create table album (
    id serial primary key,
    title varchar(250) not null,
    artist_id integer references artist(id)
);

create table inventory (
id serial primary key,
    person_id integer references person(id),
    album_id integer references album(id)
);

create table apikey (
id serial primary key,
apikey_id char(14),
    person_id int references person (id)
);
