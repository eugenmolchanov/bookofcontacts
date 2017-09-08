create database if not exists bookofcontacts;
use bookofcontacts;
drop table contact_message;
drop table contact_address;
drop table message;
drop table photo;
drop table address;
drop table attachment;
drop table phone;
drop table contact;
create table contact (id bigint auto_increment, firstName varchar(255) not null, lastName varchar(255) not null, middleName varchar(255),
birthday date, gender varchar(255), nationality varchar(255), maritalStatus varchar(255), webSite varchar(255), email varchar(255), 
employmentPlace varchar(255), primary key (id), constraint firstName_lastName_email unique(firstName, lastName, email));
create table address (id bigint auto_increment, city varchar(255), street varchar(255), houseNumber int, flatNumber int, postalIndex int, 
primary key(id));
create table attachment (id bigint auto_increment, fileName varchar(255), commentary varchar(255), recordDate date, path varchar(255), 
contact_id bigint, primary key(id), foreign key(contact_id) references contact(id));
create table phone (id bigint auto_increment, countryCode int, operatorCode int, phoneNumber bigint, phoneType varchar(255), commentary varchar(255), 
contact_id bigint, primary key(id), foreign key(contact_id) references contact(id), constraint countryCode_operatorCode_phoneNumber_phoneType unique
(countryCode, operatorCode, phoneNumber, phoneType));
create table photo (id bigint auto_increment, path varchar(255), contact_id bigint, primary key(id), foreign key(contact_id) references contact(id));
create table message (id bigint auto_increment, topic varchar(255), message longtext, primary key(id));
create table contact_message (contact_id bigint, message_id bigint, foreign key(contact_id) references contact(id), foreign key(message_id) 
references message(id));
create table contact_address (contact_id bigint, address_id bigint, foreign key (contact_id) references contact(id), foreign key (address_id) 
references address(id));