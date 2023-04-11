create database smartcity;


-- creating tables required for the project

create table citizen(
CName varchar(100) NOT NULL,
email varchar(100) NOT NULL,
phno varchar(10) NOT NULL,
citizen_id varchar(20) primary key NOT NULL,
citizen_type varchar(20) NOT NULL,
bank_id varchar(20)
);

create table locations(
address varchar(100) NOT NULL,
LName varchar(20) NOT NULL,
Descript varchar (200),
review float
);

create table utils(
utype varchar(20),
rate double,
overdue double,
citizen_id varchar(100),
FOREIGN KEY (citizen_id) REFERENCES citizen(citizen_id)
);

create table bank(
bank_id varchar(20) primary key not null,
citizen_id varchar(20),
amount double,
FOREIGN KEY (citizen_id) REFERENCES citizen(citizen_id)
);

create table transport(
ride_id varchar(20) primary key not null,
ride_type varchar(20),
Availability boolean,
fare double,
Sourc varchar(100),
dest varchar(100),
citizen_id varchar(20),
FOREIGN KEY (citizen_id) REFERENCES citizen(citizen_id)
);

create table dist(
sourc varchar(100),
destination varchar(100),
rateperkm double,
ride_type varchar(20),
distance double
);

