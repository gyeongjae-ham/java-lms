create table course (
    id bigint generated by default as identity,
    title varchar(255) not null,
    generation int not null,
    creator_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table ns_user (
    id bigint generated by default as identity,
    user_id varchar(20) not null,
    password varchar(20) not null,
    name varchar(20) not null,
    email varchar(50),
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table question (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    title varchar(100) not null,
    writer_id bigint,
    primary key (id)
);

create table answer (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    question_id bigint,
    writer_id bigint,
    primary key (id)
);

create table delete_history (
    id bigint not null,
    content_id bigint,
    content_type varchar(255),
    created_date timestamp,
    deleted_by_id bigint,
    primary key (id)
);

create table session (
    id bigint generated by default as identity,
    course_id bigint,
    price bigint not null default 0,
    status varchar(100) not null,
    start_at timestamp not null,
    end_at timestamp not null,
    byte_size bigint,
    width int,
    height int,
    extension varchar(100),
    type varchar(100) not null,
    max_student_size int not null default 0,
    primary key (id)
);

create table new_session (
    id bigint generated by default as identity,
    course_id bigint,
    price bigint not null default 0,
    session_status varchar(100) not null,
    register_status varchar(100) not null,
    start_at timestamp not null,
    end_at timestamp not null,
    byte_size bigint,
    width int,
    height int,
    extension varchar(100),
    type varchar(100) not null,
    max_student_size int not null default 0,
    primary key (id)
);

create table new_session2 (
    id bigint generated by default as identity,
    course_id bigint,
    price bigint not null default 0,
    session_status varchar(100) not null,
    register_status varchar(100) not null,
    start_at timestamp not null,
    end_at timestamp not null,
    type varchar(100) not null,
    max_student_size int not null default 0,
    primary key (id)
);

create table image(
    id bigint generated by default as identity,
    byte_size bigint,
    width int,
    height int,
    extension varchar(100),
    primary key (id)
);

create table session_student(
    id bigint generated by default as identity,
    session_id bigint not null,
    student_id bigint not null,
    primary key (id)
);

create table session_image(
    id bigint generated by default as identity,
    session_id bigint not null,
    image_id bigint not null,
    primary key (id)
);
