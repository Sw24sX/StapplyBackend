create table "app" (
    "id" int8 primary key,
    "name" varchar(255) not null,
    "developer" varchar(255) not null,
    "avatar_src" varchar(255) not null,
    "google_play_id" varchar(255),
    "app_store_id" varchar(255),
    "app_gallery_id" varchar(255),
    "description" varchar(255),
    "score_google_play" float,
    "score_app_store" float,
    "score_app_gallery" float
);

create table "users" (
    "id" int8 primary key,
    "username" varchar(255) not null unique,
    "first_name" varchar(255) not null,
    "last_name" varchar(255) not null,
    "email" varchar(255) not null unique,
    "password" varchar(255) not null,
    "role_code" smallint not null
);