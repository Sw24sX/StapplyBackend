DROp SEQUENCE IF EXISTS "hibernate_sequence";
DROP TABLE IF EXISTS "public.User" CASCADE;
DROP TABLE IF EXISTS "public.Store" CASCADE;
DROP TABLE IF EXISTS "public.App" CASCADE;
DROP TABLE IF EXISTS "public.User_App" CASCADE;
DROP TABLE IF EXISTS "public.Comment" CASCADE;
DROP TABLE IF EXISTS "public.User_Info" CASCADE;

create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE "public.User" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "surname" varchar(255) NOT NULL,
    "login" varchar(255) NOT NULL UNIQUE,
    "password" varchar(255) NOT NULL,
    "role_code" smallint NOT NULL DEFAULT 0
);

CREATE TABLE "User_Info" (
    "id" int8 NOT NULL PRIMARY KEY,
    "email" varchar(255) NOT NULL,
    "user_id" int8 NOT NULL REFERENCES "public.User" ("id")
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
    "user_id" int8 NOT NULL REFERENCES "public.User" ("id"),
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
