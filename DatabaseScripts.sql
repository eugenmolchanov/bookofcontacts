CREATE DATABASE IF NOT EXISTS bookofcontacts;
USE bookofcontacts;
DROP TABLE contact_message;
DROP TABLE contact_phone;
DROP TABLE contact_attachment;
DROP TABLE message;
DROP TABLE phone;
DROP TABLE contact;
DROP TABLE address;
DROP TABLE photo;
DROP TABLE attachment;
CREATE TABLE address (
  id          BIGINT AUTO_INCREMENT,
  country     VARCHAR(255),
  city        VARCHAR(255),
  street      VARCHAR(255),
  houseNumber INT,
  flatNumber  INT,
  postalIndex INT,
  PRIMARY KEY (id)
);
CREATE TABLE photo (
  id   BIGINT AUTO_INCREMENT,
  path VARCHAR(255),
  uuid VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE contact (
  id              BIGINT AUTO_INCREMENT,
  firstName       VARCHAR(255) NOT NULL,
  lastName        VARCHAR(255) NOT NULL,
  middleName      VARCHAR(255),
  birthday        DATE,
  gender          VARCHAR(255),
  nationality     VARCHAR(255),
  maritalStatus   VARCHAR(255),
  webSite         VARCHAR(255),
  email           VARCHAR(255),
  employmentPlace VARCHAR(255),
  photo_id        BIGINT,
  address_id      BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (photo_id) REFERENCES photo (id),
  FOREIGN KEY (address_id) REFERENCES address (id),
  CONSTRAINT firstName_lastName_email UNIQUE (firstName, lastName, email)
);
CREATE TABLE attachment (
  id         BIGINT AUTO_INCREMENT,
  fileName   VARCHAR(255),
  commentary VARCHAR(255),
  recordDate TIMESTAMP,
  path       VARCHAR(255),
  uuid       VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE phone (
  id           BIGINT AUTO_INCREMENT,
  countryCode  INT,
  operatorCode INT,
  phoneNumber  BIGINT,
  phoneType    VARCHAR(255),
  commentary   VARCHAR(255),
  PRIMARY KEY (id),
  CONSTRAINT countryCode_operatorCode_phoneNumber_phoneType UNIQUE (countryCode, operatorCode, phoneNumber, phoneType)
);
CREATE TABLE message (
  id          BIGINT AUTO_INCREMENT,
  topic       VARCHAR(255),
  message     LONGTEXT,
  sendingDate TIMESTAMP,
  PRIMARY KEY (id)
);
CREATE TABLE contact_message (
  contact_id BIGINT,
  message_id BIGINT,
  FOREIGN KEY (contact_id) REFERENCES contact (id),
  FOREIGN KEY (message_id)
  REFERENCES message (id)
);
CREATE TABLE contact_attachment (
  contact_id    BIGINT,
  attachment_id BIGINT,
  FOREIGN KEY (contact_id) REFERENCES contact (id),
  FOREIGN KEY (attachment_id)
  REFERENCES attachment (id)
);
CREATE TABLE contact_phone (
  contact_id BIGINT,
  phone_id   BIGINT,
  FOREIGN KEY (contact_id) REFERENCES contact (id),
  FOREIGN KEY (phone_id)
  REFERENCES phone (id)
);