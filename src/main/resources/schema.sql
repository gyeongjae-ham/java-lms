create table course
(
    id         bigint generated by default as identity,
    title      varchar(255) not null,
    creator_id bigint       not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id)
);

create table session
(
    id         bigint generated by default as identity,
    course_id       bigint       not null,
    title varchar(50) not null ,
    type            varchar(10) not null,
    status          varchar(10) not null,
    start_date      timestamp    not null,
    end_date        timestamp    not null,
    image_size      int          not null,
    image_height    int          not null,
    image_width     int          not null,
    image_name      varchar(255) not null,
    image_extension varchar(10)  not null,
    max_size        int,
    amount          int,
    creator_id      bigint       not null,
    created_at      timestamp    not null,
    updated_at      timestamp,
    primary key (id)
);


create table session_users
(
    id         bigint generated by default as identity,
    session_id    bigint not null,
    user_id  bigint not null,
    creator_id      bigint       not null,
    created_at timestamp   not null,
    updated_at timestamp,
    primary key (id)
);


create table ns_user
(
    id         bigint generated by default as identity,
    user_id    varchar(20) not null,
    password   varchar(20) not null,
    name       varchar(20) not null,
    email      varchar(50),
    created_at timestamp   not null,
    updated_at timestamp,
    primary key (id)
);

create table question
(
    id         bigint generated by default as identity,
    created_at timestamp    not null,
    updated_at timestamp,
    contents   clob,
    deleted    boolean      not null,
    title      varchar(100) not null,
    writer_id  bigint,
    primary key (id)
);

create table answer
(
    id          bigint generated by default as identity,
    created_at  timestamp not null,
    updated_at  timestamp,
    contents    clob,
    deleted     boolean   not null,
    question_id bigint,
    writer_id   bigint,
    primary key (id)
);

create table delete_history
(
    id            bigint not null,
    content_id    bigint,
    content_type  varchar(255),
    created_date  timestamp,
    deleted_by_id bigint,
    primary key (id)
);
