DROp SEQUENCE IF EXISTS "hibernate_sequence";
DROP TABLE IF EXISTS "User" CASCADE;
DROP TABLE IF EXISTS "Store" CASCADE;
DROP TABLE IF EXISTS "App" CASCADE;
DROP TABLE IF EXISTS "User_App" CASCADE;
DROP TABLE IF EXISTS "Comment" CASCADE;
DROP TABLE IF EXISTS "User_Info" CASCADE;

create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE "User" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "surname" varchar(255) NOT NULL,
    "login" varchar(255) NOT NULL UNIQUE,
    "password" varchar(255) NOT NULL,
    "role_code" smallint NOT NULL DEFAULT 0
);

CREATE TABLE "User_Info" (
    "id" int8 NOT NULL PRIMARY KEY,
    "email" varchar(255) NOT NULL
);

CREATE TABLE "Store" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL UNIQUE,
    "url" varchar(1024) NOT NULL UNIQUE
);

CREATE TABLE "App" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "info_id" int8 NOT NULL DEFAULT 0,
    "store_id" int8 NOT NULL,
    FOREIGN KEY ("store_id") REFERENCES "Store" ("id")
);

CREATE TABLE "User_App" (
    "id" int8 NOT NULL PRIMARY KEY,
    "user_id" int8 NOT NULL REFERENCES "User" ("id"),
    "app_id" int8 NOT NULL REFERENCES "App" ("id"),
    UNIQUE ("user_id", "app_id")
);

CREATE TABLE "Comment" (
    "id" int8 NOT NULL PRIMARY KEY,
    "date" DATE NOT NULL,
    "text" varchar(1024) NOT NULL,
    "rate" int4 NOT NULL,
    "app_id" int8 NOT NULL REFERENCES "App" ("id")
);
