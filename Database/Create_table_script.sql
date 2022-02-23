drop table weather;
drop table requests;
drop table users;
drop table zip_codes;
drop table cities;

create table if not exists cities(
    city_id serial primary key,
    city_name varchar(40),
    lat_num decimal(8,6) not null,
    long_num decimal(9,6) not null
);

create table if not exists zip_codes(
    zip_num int4 primary key,
    lat_num decimal(8,6) not null,
    long_num decimal(9,6) not null
);

create table if not exists users(
    id serial primary key,
    phone_num BIGINT not null,
    city_id int4 references cities(city_id),
    zip_num int4 references zip_codes (zip_num)
);

create table if not exists requests(
    req_id serial primary key,
    req_date TIMESTAMPTZ not null default CURRENT_TIMESTAMP::TIMESTAMPTZ,
    city_id int4 references cities(city_id),
    zip_num int4 references zip_codes(zip_num)
);

create table if not exists weather(
    weather_id serial primary key,
    date TIMESTAMPTZ not null,
    temperature float not null,
    feels_like float not null,
    pressure float not null,
    humidity float not null,
    wind_speed float not null,
    description varchar(20) not null
);