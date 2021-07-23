CREATE TABLE "public.User" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "surname" varchar(255) NOT NULL,
    "login" varchar(255) NOT NULL UNIQUE,
    "password" varchar(255) NOT NULL
);

CREATE TABLE "public.Store" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL UNIQUE
);

CREATE TABLE "public.App" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "info_id" int8 NOT NULL DEFAULT 0,
    "store_id" int8 NOT NULL,
    FOREIGN KEY ("store_id") REFERENCES "public.Store" ("id")
);

CREATE TABLE "public.User_App" (
    "id" int8 NOT NULL PRIMARY KEY,
    "user_id" int8 NOT NULL REFERENCES "public.User" ("id"),
    "app_id" int8 NOT NULL REFERENCES "public.App" ("id"),
    UNIQUE ("user_id", "app_id")
);

CREATE TABLE "public.Comment" (
    "id" int8 NOT NULL PRIMARY KEY,
    "date" DATE NOT NULL,
    "text" varchar(1024) NOT NULL,
    "rate" int4 NOT NULL,
    "app_id" int8 NOT NULL REFERENCES "public.App" ("id")
);
