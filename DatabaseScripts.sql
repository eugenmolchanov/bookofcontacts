USE book_of_contacts_by_molchanov;
DROP TABLE contact_message;
DROP TABLE message;
DROP TABLE contact;
DROP TABLE phone;
DROP TABLE photo;
DROP TABLE attachment;
DROP DATABASE book_of_contacts_by_molchanov;
CREATE DATABASE IF NOT EXISTS book_of_contacts_by_molchanov
  COLLATE utf8_general_ci;
USE book_of_contacts_by_molchanov;

CREATE TABLE photo (
  id   BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  path VARCHAR(255),
  uuid VARCHAR(255),
  PRIMARY KEY (id)
)
  ENGINE InnoDB;

CREATE TABLE contact (
  id               BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  first_name       VARCHAR(255)                   NOT NULL,
  last_name        VARCHAR(255)                   NOT NULL,
  middle_name      VARCHAR(255),
  birth_date       DATE,
  gender           ENUM ('Мужчина', 'Женщина'),
  nationality      VARCHAR(255),
  marital_status   ENUM ('Не замужем', 'Не женат', 'Замужем', 'Женат', 'Состою в гражданском браке', 'Вдовец', 'Вдова'),
  website          VARCHAR(255),
  email            VARCHAR(255),
  employment_place VARCHAR(255),
  contact_group    ENUM ('Семья', 'Друзья', 'Коллеги', 'Соседи'),
  country          VARCHAR(255),
  city             VARCHAR(255),
  street           VARCHAR(255),
  house_number     VARCHAR(255),
  flat_number      INT,
  postcode         INT,
  photo_id         BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (photo_id) REFERENCES photo (id),
  CONSTRAINT first_name_last_name_email UNIQUE (first_name, last_name, email)
)
  ENGINE InnoDB;

CREATE TABLE attachment (
  id          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  file_name   VARCHAR(255),
  commentary  VARCHAR(255),
  record_date TIMESTAMP,
  path        VARCHAR(255),
  uuid        VARCHAR(255),
  contact_id  BIGINT UNSIGNED not null,
  PRIMARY KEY (id),
  FOREIGN KEY (contact_id) REFERENCES contact (id)
)
  ENGINE InnoDB;

CREATE TABLE phone (
  id            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  country_code  VARCHAR(10),
  operator_code INT,
  phone_number  BIGINT,
  phone_type    ENUM ('Домашний', 'Рабочий', 'Сотовый'),
  commentary    VARCHAR(255),
  contact_id    BIGINT UNSIGNED not null,
  PRIMARY KEY (id),
  FOREIGN KEY (contact_id) REFERENCES contact (id)
)
  ENGINE InnoDB;

CREATE TABLE message (
  id           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  topic        VARCHAR(255),
  message      LONGTEXT,
  sending_date TIMESTAMP,
  is_deleted   BIT DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE InnoDB;

CREATE TABLE contact_message (
  contact_id BIGINT UNSIGNED NOT NULL,
  message_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (contact_id) REFERENCES contact (id),
  FOREIGN KEY (message_id) REFERENCES message (id)
)
  ENGINE InnoDB;