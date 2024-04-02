create table Projet
(
    id     int auto_increment
        primary key,
    name   varchar(40) not null,
    budget double      not null
);

create table employee
(
    id     int auto_increment
        primary key,
    name   varchar(40) not null,
    email  varchar(30) not null,
    post   varchar(30) not null,
    skills varchar(40) not null
);

create table employee_skills
(
    ID          int auto_increment
        primary key,
    SKILL       varchar(255) null,
    employee_id int          null,
    constraint FK_employee_skills_employee_id
        foreign key (employee_id) references employee (id)
);

create table employee_project
(
    CHARGE      double null,
    EMPLOYEE_ID int    not null,
    PROJECT_ID  int    not null,
    primary key (EMPLOYEE_ID, PROJECT_ID),
    constraint FK_employee_project_EMPLOYEE_ID
        foreign key (EMPLOYEE_ID) references employee (id),
    constraint FK_employee_project_PROJECT_ID
        foreign key (PROJECT_ID) references project (ID)
);

