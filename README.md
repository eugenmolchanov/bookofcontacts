# README #

### Preparation for proper and stable work of application ###
In Dao,Services and Web modules in com.itechart.javalab.firstproject.resources folders you should put credentials for database in credentials.properties files and credentials for mail in mail_credentials.properties:

user_name = //user name in database;

password = //password in database;

from = //put here your email address which will send messages;

password = //password for this email;

You should set directories like you want for user photos and attachments in web/src/main/com.itechart.javalab.firstproject.resources/data.properties in line 1, 
web/src/main/com.itechart.javalab.firstproject.resources/log4j.properties in line 3 and services/src/main/com.itechart.javalab.firstproject.resources/log4j.properties in line 3 for logger.

Scripts for database in file DatabaseScripts.sql.