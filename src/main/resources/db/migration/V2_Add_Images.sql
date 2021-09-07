create table "image" (
    "id" int8 primary key,
    "url" varchar(500) not null,
    "app_id" int8 references "app"("id")
);